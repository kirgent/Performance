#!/bin/bash
### ### ### OLD_API !!! req=ChangeReminders !!!
# written by Kirill Grushin (kirill.grushin@dev.zodiac.tv)

curlwrap="curl"
#curlwrap="curl -v"
#curlwrap="curl -v --connect-timeout 5"

#RED='\033[0;31m'
#GREEN='\033[0;32m'
#GRAY='\033[0;37m'

mac="$1"
action="$2"
param="$3"
count_reminders="$4"

ams_ip="172.30.81.4"
#ams_ip="172.30.82.132"
#ams_ip="172.30.112.19"
ams_port="8080"

count_iterations=1
reminderOffset=0
reminderOffset_new=10

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
charterapi="$charterapi_b"

url="ams/Reminders?req=ChangeReminders"

function generate_params(){
if [ "$param" == "48" ]; then reminder="reminder48"; source test_reminder48.sh
elif [ "$param" == "288" ]; then reminder="reminder288"; source test_reminder288.sh
elif [ "$param" == "720" ]; then reminder="reminder720"; source test_reminder720.sh
else reminder="reminder48"; source test_reminder48.sh
fi
}

logfile="test_reminder.log"
logwrap="tee -a $logfile"
startmessage="[DBG] `date "+%a %b %d %T %N %Z %Y"`: NEW START: ams_ip=$ams_ip, count_reminders=$param, count_iterations=$count_iterations, RACK_DATE=( ${RACK_DATE[@]} ), RACK_CHANNELS=( ${RACK_CHANNELS[@]} )"

synopsys="\nNAME
\ttest_reminder.sh - script for Add / Edit / Delete / Purge reminders on mac and also check registration / registration on AMS.
SYNOPSYS
\ttest_reminder.sh [mac] [OPERATION] [COUNT]
\nDESCRIPTION
\tmac is a box mac, e.g. A0722CB1AF24
\tOPERATION is a action for curl/json e.g. Add, Edit (it's really will be Delete+Add), Delete, Purge, Change
\tCOUNT is a count of reminders, can be 48, 288, 720 in one curl request
OPTIONS
\ttest_Reminder.sh                   - print this help
\ttest_reminder.sh mac        - send curl for checking registration
\ttest_reminder.sh mac Check  - send curl for checking registration
\ttest_reminder.sh mac Change - send curl for changing registration to new AMS
\ttest_reminder.sh mac Purge  - clear all reminders
\ttest_reminder.sh mac Add    - add reminders (cyclically)
\ttest_reminder.sh mac Edit   - delete reminders with current reminderOffset + add reminders with reminderOffset_new (cyclically)
\ttest_reminder.sh mac Delete - delete reminders (cyclically)
\ttest_reminder.sh mac All    - add + edit + delete reminders (cyclically)
DEFAULT CURRENT SETTINGS:
\tAMS: $ams_ip
\tcharterapi: $charterapi
\tcount iterations: $count_iterations
\tcount reminders: $count_reminders
STATUSCODES:
\tcode of the reminder processing result, one of the following:
\t0 - requested action with the reminder was accomplished successfully
\t2 - reminder is set for time in the past
\t3 - reminder is set for unknown channel
\t4 - reminder is unknown, applies to reminder deletion attempts
\thttps://svn.developonbox.ru/Charter_Docs/Projects/Cloud_Based_Guide/Requirements/APIs/Reminders%20Propagation%20API-v6.docx"

statuscodes="code of the reminder processing result, one of the following:
0 - requested action with the reminder was accomplished successfully
2 - reminder is set for time in the past
3 - reminder is set for unknown channel
4 - reminder is unknown, applies to reminder deletion attempts"
########################### ########################### ########################### ########################### ###########################
########################### ########################### ########################### ########################### ###########################
########################### ########################### ########################### ########################### ###########################

#if [ ! -x "`which time`" ]; then echo -e "No time detected! Please check.$synopsys"; exit
#else time="/usr/bin/time -f 'real %Es' -o $logfile -a"
#fi

if [ ! -x "`which curl`" ]; then echo -e "No curl detected! please check.$synopsys"; exit; fi

########################### main() ###########################
if [ -z "$mac" ]; then
echo -e "No mac specified!$synopsys"|${logwrap}; exit;
elif [ `expr length "$mac"` -ne "12" ]; then echo -e "Incorrect mac specified!$synopsys"|${logwrap}; exit
fi

if [ -z "$count_reminders" ]; then count_reminders=1; fi

if [ ! -z "$action" ]; then

if [ "$action" == "Check" ]; then
echo "Checking registration:"
${curlwrap} ${charterapi_a}/${postfix_settings}/amsIp/${mac}; echo
${curlwrap} ${charterapi_b}/${postfix_settings}/amsIp/${mac}; echo
${curlwrap} ${charterapi_c}/${postfix_settings}/amsIp/${mac}; echo
${curlwrap} ${charterapi_d}/${postfix_settings}/amsIp/${mac}; echo


elif [ "$action" == "Change" ]; then
if [ ! -z "$param" ]; then ams_ip=${param}; fi
echo "Changing registration to AMS $ams_ip"
echo "Used charterapi: $charterapi"
json_change='{"settings":{"groups":[{"options":[],"id":"STB'${mac}'","type":"device-stb","amsid":"'${ams_ip}'"}]}}'
${curlwrap} -H 'Content-Type:application/json' -d ${json_change} "$charterapi_c/$postfix_settings?requestor=AMS"; echo
${curlwrap} -H 'Content-Type:application/json' -d ${json_change} "$charterapi_b/$postfix_settings?requestor=AMS"; echo
${curlwrap} -H 'Content-Type:application/json' -d ${json_change} "$charterapi_c/$postfix_settings?requestor=AMS"; echo
${curlwrap} -H 'Content-Type:application/json' -d ${json_change} "$charterapi_d/$postfix_settings?requestor=AMS"; echo



elif [ "$action" == "Purge" ]; then
echo "[DBG] `date "+%a %b %d %T %N %Z %Y"`: NEW START: ams_ip=$ams_ip, Purge ---> (_|_)"|${logwrap}
/usr/bin/time -f 'real %Es' -o ${logfile} -a ${curlwrap} 'http://'${ams_ip}':'${ams_port}'/'$url'' -H 'Content-type: application/json' -d '{ "deviceId": '${mac}', "reminders": [{"operation": "Purge"}]}'; echo
#/usr/bin/time -f 'real %Es' curl -s 'http://'$ams_ip':'$ams_port'/'$url'' -H 'Content-type: text/plain' --data '{ "deviceId": '$mac', "reminders": [{"operation": "Purge"}]}' 2>&1|tee -a $logfile; echo


elif [ "$action" == "All" ]; then
generate_params
echo ${startmessage}|${logwrap}
for (( i=1; i<=$count_iterations; i++)); do for reminderProgramStart in ${RACK_DATE[@]}; do for reminderChannelNumber in ${RACK_CHANNELS[@]}; do
${reminder} Add ${reminderOffset}; sleep 1
${reminder} Edit ${reminderOffset} ${reminderOffset_new}; sleep 1
${reminder} Delete ${reminderOffset_new}; sleep 1
done; done; done
echo "$statuscodes"


elif [ "$action" == "Add" ]; then
generate_params
echo ${startmessage}|${logwrap}
for (( i=1; i<=$count_iterations; i++)); do for reminderProgramStart in ${RACK_DATE[@]}; do for reminderChannelNumber in ${RACK_CHANNELS[@]}; do
${reminder} ${action} ${reminderOffset};  sleep 1; done; done; done


elif [ "$action" == "Edit" ]; then
generate_params
echo ${startmessage}|${logwrap}
for (( i=1; i<=$count_iterations; i++)); do for reminderProgramStart in ${RACK_DATE[@]}; do for reminderChannelNumber in ${RACK_CHANNELS[@]}; do
${reminder} ${action} ${reminderOffset} ${reminderOffset_new}; done; done; done


elif [ "$action" == "Delete" ]; then
generate_params
echo ${startmessage}|${logwrap}
for (( i=1; i<=$count_iterations; i++)); do for reminderProgramStart in ${RACK_DATE[@]}; do for reminderChannelNumber in ${RACK_CHANNELS[@]}; do
${reminder} ${action} ${reminderOffset}; sleep 1; done; done; done

else
echo -e "Incorrect action specified!$synopsys"|${logwrap}; exit;


fi
else
echo "Checking registration:"
${curlwrap} ${charterapi_a}/${postfix_settings}/amsIp/${mac}; echo
${curlwrap} ${charterapi_b}/${postfix_settings}/amsIp/${mac}; echo
${curlwrap} ${charterapi_c}/${postfix_settings}/amsIp/${mac}; echo
${curlwrap} ${charterapi_d}/${postfix_settings}/amsIp/${mac}; echo

fi
########################### main() end ###########################
