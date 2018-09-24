#!/bin/bash

curlwrap="curl"
macaddress="$1"

if [ -z ${macaddress} ]; then echo -e "No macaddress specified!"; exit;
elif [ `expr length ${macaddress}` -ne "12" ]; then echo -e "Incorrect macaddress specified!"; exit
fi

${curlwrap} http://spec.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings/amsIp/${macaddress};echo
${curlwrap} http://specb.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings/amsIp/${macaddress};echo
${curlwrap} http://specc.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings/amsIp/${macaddress};echo
${curlwrap} http://specd.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings/amsIp/${macaddress};echo
