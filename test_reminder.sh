#!/bin/bash
# written by Kirill Grushin (kirill.grushin@dev.zodiac.tv)

curlwrap="curl"
#curlwrap="curl -v"
#curlwrap="curl -v --connect-timeout 5"

#RED='\033[0;31m'
#GREEN='\033[0;32m'
#GRAY='\033[0;37m'

show_generated_json=true
show_extra_info=true

mac="$1"
action="$2"
param="$3"

ams_ip="172.30.81.4"
#ams_ip="172.30.82.132"
#ams_ip="172.30.112.19"
ams_port="8080"

random="-1"
count_iterations=1
count_reminders=1
reminderChannelNumber=3
reminderProgramId="EP002960010113"
#reminderOffset=0
reminderOffset_new=10
#reminderScheduleId=12345
#reminderId=12345

RACK_DATE=( `date +%Y-%m-%d -d "tomorrow"` )
#RACK_DATE=( `date +%Y-%m-%d -d "tomorrow +1day"` `date +%Y-%m-%d -d "tomorrow +2day"` `date +%Y-%m-%d -d "tomorrow +3day"` `date +%Y-%m-%d -d "tomorrow +4day"` `date +%Y-%m-%d -d "tomorrow +5day"` `date +%Y-%m-%d -d "tomorrow +6day"` `date +%Y-%m-%d -d "tomorrow +7day"` `date +%Y-%m-%d -d "tomorrow +8day"` `date +%Y-%m-%d -d "tomorrow +9day"` `date +%Y-%m-%d -d "tomorrow +10day"` )

RACK_CHANNELS=( 2 )
#RACK_CHANNELS=( 2 3 4 5 6 7 8 9 12 13 14 16 18 19 22 23 25 28 30 31 32 33 37 38 41 44 46 49 50 51 )

charterapi_a="http://spec.partnerapi.engprod-charter.net/api/pub"
charterapi_b="http://specb.partnerapi.engprod-charter.net/api/pub"
charterapi_c="http://specc.partnerapi.engprod-charter.net/api/pub"
charterapi_d="http://specd.partnerapi.engprod-charter.net/api/pub"
#charterapi_d="http://specd.partnerapi.engprod-charter.net/api/pub"
postfix_settings="networksettingsmiddle/ns/settings"

function generate_params(){
if [ "$action" == "All" ]; then
source test_reminder_add.sh
source test_reminder_modify.sh
source test_reminder_delete.sh
if [ "$count_reminders" == "48" ]; then reminder_add="add48"; reminder_modify="modify48"; reminder_delete="delete48"
elif [ "$count_reminders" == "288" ]; then reminder_add="add288"; reminder_modify="modify288"; reminder_delete="delete288"
elif [ "$count_reminders" == "720" ]; then reminder_add="add720"; reminder_modify="modify720"; reminder_delete="delete720"
else reminder_add="add1"; reminder_modify="modify1"; reminder_delete="delete1"
fi

elif [ "$action" == "Add" ]; then
source test_reminder_add.sh
if [ "$count_reminders" == "48" ]; then reminder_add="add48"
elif [ "$count_reminders" == "288" ]; then reminder_add="add288"
elif [ "$count_reminders" == "720" ]; then reminder_add="add720"
else reminder_add="add1"
fi

elif [ "$action" == "Modify" ]; then
source test_reminder_modify.sh
if [ "$count_reminders" == "48" ]; then reminder_modify="modify48"
elif [ "$count_reminders" == "288" ]; then reminder_modify="modify288"
elif [ "$count_reminders" == "720" ]; then reminder_modify="modify720"
else reminder_modify="modify1"
fi

elif [ "$action" == "Delete" ]; then
source test_reminder_delete.sh
if [ "$count_reminders" == "48" ]; then reminder_delete="delete48"
elif [ "$count_reminders" == "288" ]; then reminder_delete="delete288"
elif [ "$count_reminders" == "720" ]; then reminder_delete="delete720"
else reminder_delete="delete1"
fi
fi
}


function reminderOffset_random(){
number=$RANDOM
return ${number}
# If you need a random int within a certain range, use the 'modulo' operator.
# This returns the remainder of a division operation.
#RANGE=500
#number=$RANDOM
#let "number %= $RANGE"
#echo "Random number less than $RANGE  ---  $number"
#echo
}

function reminderId_random(){
number=$RANDOM
echo ${number}
}

function reminderScheduleId_random(){
number=$RANDOM
echo ${number}
}

logfile="test_reminder.log"
logwrap="tee -a $logfile"
startmessage="[DBG] `date "+%a %b %d %T %N %Z %Y"`: NEW START: ams_ip=$ams_ip, count_reminders=$count_reminders, count_iterations=$count_iterations, RACK_DATE=( ${RACK_DATE[@]} ), RACK_CHANNELS=( ${RACK_CHANNELS[@]} ), reminderProgramId=$reminderProgramId, reminderOffset=$reminderOffset, reminderOffset_new=$reminderOffset_new, reminderScheduleId=$reminderScheduleId, reminderId=$reminderId"

synopsys="\nNAME
\ttest_reminder.sh - script for Add / Edit / Delete / Purge reminders on mac and also check registration / registration on AMS.
SYNOPSYS
\ttest_reminder.sh [mac] [OPERATION] [COUNT]
\nDESCRIPTION
\tmac is a box mac, e.g. A0722CB1AF24
\tOPERATION is a action for curl/json e.g. Add, Edit (it's really will be Delete+Add), Delete, Purge, Change
\tCOUNT is a count of reminders, can be 48, 288, 720 in one curl request
OPTIONS
\ttest_Reminder.sh            - print this help
\ttest_reminder.sh mac        - send curl for checking registration
\ttest_reminder.sh mac Check  - send curl for checking registration
\ttest_reminder.sh mac Change - send curl for changing registration to new AMS
\ttest_reminder.sh mac Purge  - clear all reminders
\ttest_reminder.sh mac Add    - add reminders (cyclically)
\ttest_reminder.sh mac Edit   - delete reminders with current offset + add reminders with new_offset (cyclically)
\ttest_reminder.sh mac Delete - delete reminders (cyclically)
\ttest_reminder.sh mac All    - add + edit + delete reminders (cyclically)
CURRENT SETTINGS
\tAMS: $ams_ip
\tcount of reminders in one request: $param
\tcount iterations: $count_iterations
STATUSCODE
\tcode of the reminder processing result, one of the following:
\t0 - requested operation with the reminder was accomplished successfully. Always returned for \"Reminders Purge\" request (Request ID=3)
\t1 - TBD
\t2 - reminder is set for time in the past. Applies to \"Reminders Add\" request (Request ID=0)
\t3 - reminder is set for unknown channel. \"Reminders Add\" request (Request ID=0)
\t4 - reminder is unknown. Applies to \"Reminders Delete\" request (Request ID=1) and \"Reminders Modify\" request (Request ID=2)
\t5 - reminder with provided pair of identifiers (reminderScheduleId and reminderId) is already set \"Reminders Add\" request (Request ID=0)
\thttps://svn.developonbox.ru/Charter_Docs/Projects/Cloud_Based_Guide/Requirements/APIs/Reminders%20Propagation%20API-v6.docx"

statuscodes="code of the reminder processing result, one of the following:
0 - requested operation with the reminder was accomplished successfully. Always returned for \"Reminders Purge\" request (Request ID=3)
1 - the number of reminders for the STB exceeded the limitation Applies to \"Reminders Add\" request (Request ID=0)
2 - reminder is set for time in the past. Applies to \"Reminders Add\" request (Request ID=0)
3 - reminder is set for unknown channel. \"Reminders Add\" request (Request ID=0)
4 - reminder is unknown. Applies to \"Reminders Delete\" request (Request ID=1) and \"Reminders Modify\" request (Request ID=2)
5 - reminder with provided pair of identifiers (reminderScheduleId and reminderId) is already set \"Reminders Add\" request (Request ID=0)"

########################### ########################### ########################### ########################### ###########################
########################### ########################### ########################### ########################### ###########################
########################### ########################### ########################### ########################### ###########################



if [ ! -x "`which time`" ]; then echo -e "No time detected! Please check."; exit
else time="/usr/bin/time -f 'real %Es' -o $logfile -a"
fi

if [ ! -x "`which curl`" ]; then echo -e "No curl binary detected! please check curl tool."; exit; fi

########################### main() ###########################
if [ -z "$mac" ]; then
echo -e "No mac specified!$synopsys"|${logwrap}; exit;
elif [ `expr length "$mac"` -ne "12" ]; then echo -e "Incorrect mac specified!$synopsys"|${logwrap}; exit
fi

if [ ! -z "$param" ]; then count_reminders=${param}; echo "!!!!!!!!!!!!!!!!!!!"; else count_reminders=1; fi

if [ ! -z "$action" ]; then

echo ${startmessage}|${logwrap}

if [ "$action" == "Check" ]; then
echo "Checking registration:"
${curlwrap} ${charterapi_a}/${postfix_settings}/amsIp/${mac}; echo
${curlwrap} ${charterapi_b}/${postfix_settings}/amsIp/${mac}; echo
${curlwrap} ${charterapi_c}/${postfix_settings}/amsIp/${mac}; echo
${curlwrap} ${charterapi_d}/${postfix_settings}/amsIp/${mac}; echo


elif [ "$action" == "Change" ]; then
if [ ! -z "$param" ]; then ams_ip=${param}; fi
echo "Changing registration to AMS $ams_ip"
json_change='{"settings":{"groups":[{"options":[],"id":"STB'${mac}'","type":"device-stb","amsid":"'${ams_ip}'"}]}}'
${curlwrap} -H 'Content-Type:application/json' -d ${json_change} "$charterapi_a/$postfix_settings?requestor=AMS"; echo
${curlwrap} -H 'Content-Type:application/json' -d ${json_change} "$charterapi_b/$postfix_settings?requestor=AMS"; echo
${curlwrap} -H 'Content-Type:application/json' -d ${json_change} "$charterapi_c/$postfix_settings?requestor=AMS"; echo
${curlwrap} -H 'Content-Type:application/json' -d ${json_change} "$charterapi_d/$postfix_settings?requestor=AMS"; echo


elif [ "$action" == "Purge" ]; then
for (( i=1; i<=$count_iterations; i++)); do
url="ams/Reminders?req=purge"
echo "[DBG] `date "+%a %b %d %T %N %Z %Y"`: ams_ip=$ams_ip, iteration=$i/$count_iterations, Purge ---> (_|_)"|${logwrap}
/usr/bin/time -f 'real %Es' -o ${logfile} -a ${curlwrap} 'http://'${ams_ip}':'${ams_port}'/'${url}'' -H 'Content-type: application/json' -d '{ "deviceId": '$mac', "reminders": []}'; echo; done


elif [ "$action" == "All" ]; then
generate_params
reminderOffset=${random}
reminderOffset_new=10
reminderScheduleId=${random}
reminderId=${random}
for (( i=1; i<=$count_iterations; i++)); do
for reminderProgramStart in ${RACK_DATE[@]}; do
for reminderChannelNumber in ${RACK_CHANNELS[@]}; do
${reminder_add} ${mac} ${reminderProgramStart} ${reminderChannelNumber} ${reminderProgramId} ${reminderOffset} ${reminderScheduleId} ${reminderId}; sleep 1
${reminder_modify} ${mac} ${reminderProgramStart} ${reminderChannelNumber} ${reminderProgramId} ${reminderOffset_new} ${reminderScheduleId} ${reminderId}; sleep 1
${reminder_delete} ${mac} ${reminderScheduleId} ${reminderId};  sleep 1; done; done; done


elif [ "$action" == "Add" ]; then
generate_params
reminderOffset=${random}
reminderOffset_new=10
reminderScheduleId=${random}
reminderId=${random}
echo "main reminderScheduleId=$reminderScheduleId"
echo "main reminderId=$reminderId"
for (( i=1; i<=$count_iterations; i++)); do for reminderProgramStart in ${RACK_DATE[@]}; do for reminderChannelNumber in ${RACK_CHANNELS[@]}; do
${reminder_add} ${mac} ${reminderProgramStart} ${reminderChannelNumber} ${reminderProgramId} ${reminderOffset} ${reminderScheduleId} ${reminderId}; sleep 1; done; done; done


elif [ "$action" == "Modify" ]; then
generate_params
reminderOffset=${random}
reminderOffset_new=10
reminderScheduleId=12345
reminderId=12345
for (( i=1; i<=$count_iterations; i++)); do for reminderProgramStart in ${RACK_DATE[@]}; do for reminderChannelNumber in ${RACK_CHANNELS[@]}; do
${reminder_modify} ${mac} ${reminderProgramStart} ${reminderChannelNumber} ${reminderProgramId} ${reminderOffset_new} ${reminderScheduleId} ${reminderId};  sleep 1; done; done; done


elif [ "$action" == "Delete" ]; then
generate_params
reminderScheduleId=12345
reminderId=12345
for (( i=1; i<=$count_iterations; i++)); do for reminderProgramStart in ${RACK_DATE[@]}; do for reminderChannelNumber in ${RACK_CHANNELS[@]}; do
${reminder_delete} ${mac} ${reminderScheduleId} ${reminderId};  sleep 1; done; done; done

else
echo -e "Incorrect action specified!"; exit;


fi
else
echo "Checking registration:"
${curlwrap} ${charterapi_a}/${postfix_settings}/amsIp/${mac}; echo
${curlwrap} ${charterapi_b}/${postfix_settings}/amsIp/${mac}; echo
${curlwrap} ${charterapi_c}/${postfix_settings}/amsIp/${mac}; echo
${curlwrap} ${charterapi_d}/${postfix_settings}/amsIp/${mac}; echo

fi
########################### main() end ###########################
