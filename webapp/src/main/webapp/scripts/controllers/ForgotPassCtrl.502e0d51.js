define([ "siglasApp", "services/AuthService" ], function(siglasApp) {
    siglasApp.controller("ForgotPassCtrl", [ "$scope", "$rootScope", "$location", "$filter", "toastr", "AuthService", function($scope, $rootScope, $location, $filter, toastr, AuthService) {
        function submit(valid) {
            valid && (self.loading = !0, AuthService.forgotPass(self.email).then(function(result) {
                toastr.success($filter("translate")("auth.forgotpass.success.message"), $filter("translate")("auth.forgotpass.success.title"), {
                    tapToDismiss: !0,
                    autoDismiss: !1
                }), self.loading = !1;
            }, function(err) {
                self.loading = !1, toastr.error($filter("translate")("login.error.wrongData"), $filter("translate")("login.error.error"));
            }));
        }
        console.log("ForgotPassCtrl");
        var self = this;
        if (self.submit = submit, self.email = "", $rootScope.loggedUser) return $location.path("/");
    } ]);
});