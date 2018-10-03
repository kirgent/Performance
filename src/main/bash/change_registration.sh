#!/bin/bash

curlwrap="curl"
macaddress="$1"
ams_ip="$2"
if [ -z ${macaddress} ]; then echo -e "No macaddress specified!"; exit;
elif [ `expr length "$macaddress"` -ne "12" ]; then echo -e "Incorrect macaddress specified!"; exit
fi

if [ -z ${ams_ip} ]; then echo -e "No ams_ip specified!"; exit; fi

${curlwrap} -H "Content-Type:application/json" -d '{"settings":{"groups":[{"options":[],"id":"STB'${macaddress}'","type":"device-stb","amsid":"'${ams_ip}'"}]}}' "http://spec.partnerapi.engprod-charter.net/common/pub/networksettingsmiddle/ns/settings?requestor=AMS";echo
${curlwrap} -H "Content-Type:application/json" -d '{"settings":{"groups":[{"options":[],"id":"STB'${macaddress}'","type":"device-stb","amsid":"'${ams_ip}'"}]}}' "http://specb.partnerapi.engprod-charter.net/common/pub/networksettingsmiddle/ns/settings?requestor=AMS";echo
${curlwrap} -H "Content-Type:application/json" -d '{"settings":{"groups":[{"options":[],"id":"STB'${macaddress}'","type":"device-stb","amsid":"'${ams_ip}'"}]}}' "http://specc.partnerapi.engprod-charter.net/common/pub/networksettingsmiddle/ns/settings?requestor=AMS";echo
${curlwrap} -H "Content-Type:application/json" -d '{"settings":{"groups":[{"options":[],"id":"STB'${macaddress}'","type":"device-stb","amsid":"'${ams_ip}'"}]}}' "http://specd.partnerapi.engprod-charter.net/common/pub/networksettingsmiddle/ns/settings?requestor=AMS";echo
