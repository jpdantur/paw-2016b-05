define([ "siglasApp", "services/SalesService", "directives/star-rating" ], function(siglasApp) {
    siglasApp.controller("ScoreCtrl", [ "$scope", "$rootScope", "$route", "toastr", "SalesService", "$filter", function($scope, $rootScope, $route, toastr, SalesService, $filter) {
        function rateSale(valid) {
            valid && SalesService.rateWithRole(self.tx.id, self.role, {
                rating: "buyer" === self.role ? self.tx.buyerReview.rating : self.tx.sellerReview.rating,
                comment: self.comment
            }).then(function(result) {
                console.log(result), toastr.success($filter("translate")("ng.messages.reviewSuccess")), 
                self.tx[self.role + "Review"].id = -1;
            }, function(err) {
                toastr.error($filter("translate")("ng.messages.reviewError"));
            });
        }
        console.log("ScoreCtrl");
        var self = this, txId = $route.current.params.txId;
        self.role = $route.current.params.role, self.tx = null, self.rateSale = rateSale, 
        SalesService.findById(txId).then(function(tx) {
            var prop = self.role + "Review";
            tx[prop] || (console.log("setting"), _.set(tx, prop, {
                rating: 2.5
            })), self.tx = tx, console.log(self.tx), console.log(tx);
        }, function(error) {
            console.error(error);
        });
    } ]);
});