
var Sinch = function(){};

Sinch.prototype.startClient = function(success, failure, appKey, appSecret, host, userId){
    cordova.exec(success, failure, "Sinch", "start", [appKey, appSecret, host, userId]);
};

Sinch.prototype.callUser = function(success, failure,userId){
    cordova.exec(success, failure, "Sinch", "callUser",[userId]);
}

//Plug in to Cordova
cordova.addConstructor(function() {

    if (!window.Cordova) {
        window.Cordova = cordova;
    };

    if(!window.plugins) window.plugins = {};
    window.plugins.Sinch = new Sinch();
});