### ### ### OLD_API !!! req=ChangeReminders !!!
function reminder48(){
operation="$1"
reminderOffset="$2"
reminderOffset_new="$3"

echo "[DBG] `date "+%a %b %d %T %N %Z %Y"`: $operation 48rems with reminderOffset=$reminderOffset, reminderOffset_new=$reminderOffset_new): iteration="$i"/"$count_iterations", mac=$mac, reminderProgramStart=$reminderProgramStart, reminderChannelNumber=$reminderChannelNumber)"|$logwrap

if [ "$operation" != "Edit" ]; then
#/usr/bin/time -f 'real %Es' -o $logfile -a $curlwrap 'http://'$ams_ip':'$ams_port'/'$url'' -H 'Content-type: application/json' \
/usr/bin/time -f 'real %Es' -o ${logfile} -a ${curlwrap} -s 'http://'${ams_ip}':'${ams_port}'/'${url}'' -H 'Content-type: application/json' \
-d '{
"deviceId": '${mac}',
"reminders": [
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 00:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 00:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 01:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 01:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 02:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 02:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 03:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 03:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 04:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 04:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 05:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 05:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 06:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 06:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 07:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 07:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 08:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 08:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 09:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 09:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 10:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 10:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 11:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 11:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 12:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 12:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 13:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 13:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 14:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 14:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 15:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 15:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 16:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 16:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 17:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 17:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 18:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 18:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 19:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 19:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 20:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 20:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 21:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 21:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 22:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 22:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 23:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": '$operation', "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 23:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' }
]}'
echo;echo
elif [ "$operation" == "Edit" ]; then
/usr/bin/time -f 'real %Es' -o $logfile -a $curlwrap 'http://'$ams_ip':'$ams_port'/'$url'' -H 'Content-type: application/json' \
-d '{
"deviceId": '$mac',
"reminders": [
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 00:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 00:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 01:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 01:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 02:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 02:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 03:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 03:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 04:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 04:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 05:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 05:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 06:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 06:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 07:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 07:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 08:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 08:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 09:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 09:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 10:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 10:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 11:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 11:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 12:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 12:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 13:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 13:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 14:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 14:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 15:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 15:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 16:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 16:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 17:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 17:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 18:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 18:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 19:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 19:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 20:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 20:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 21:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 21:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 22:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 22:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 23:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Delete, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 23:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 00:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 00:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 01:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 01:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 02:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 02:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 03:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 03:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 04:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 04:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 05:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 05:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 06:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 06:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 07:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 07:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 08:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 08:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 09:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 09:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 10:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 10:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 11:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 11:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 12:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 12:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 13:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 13:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 14:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 14:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 15:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 15:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 16:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 16:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 17:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 17:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 18:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 18:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 19:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 19:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 20:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 20:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 21:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 21:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 22:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 22:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 23:00", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' },
 { "operation": Add, "reminderChannelNumber": '$reminderChannelNumber', "reminderProgramStart": "'$reminderProgramStart' 23:30", "reminderProgramId": 0, "reminderOffset": '$reminderOffset_new' }
]}'
echo;echo
fi
}
