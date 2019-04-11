cd ./app/build/intermediates/classes/debug/
jar cvf hotfix_pack.jar  ./com/miqt/vip/proxy/WebVideoPlayerActyProxy.class ./com/miqt/vip/proxy/ActionProxy.class ./com/miqt/vip/proxy/BaseProxy.class
dx --dex --output=../../../../../hotfix_pack.dex hotfix_pack.jar
cd ../../../../../
