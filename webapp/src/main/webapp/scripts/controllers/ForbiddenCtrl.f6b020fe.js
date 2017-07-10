define([ "siglasApp" ], function(siglasApp) {
    siglasApp.controller("ForbiddenCtrl", [ "$scope", "$rootScope", function($scope, $rootScope) {
        var self = this;
        $rootScope.loggedUser ? self.path = "/dashboard" : self.path = "/";
    } ]);
});