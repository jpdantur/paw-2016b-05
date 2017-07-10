define([ "siglasApp" ], function(siglasApp) {
    siglasApp.directive("categoryBrowser", function() {
        return {
            restrict: "E",
            replace: !0,
            transclude: !1,
            compile: function(elem, attrs) {},
            link: function(scope, elem, attrs, ctrl) {
                var firstPassword = "#" + attrs.pwCheck;
                elem.add(firstPassword).on("keyup", function() {
                    scope.$apply(function() {
                        var v = elem.val() === $(firstPassword).val();
                        ctrl.$setValidity("pwmatch", v);
                    });
                });
            }
        };
    });
});