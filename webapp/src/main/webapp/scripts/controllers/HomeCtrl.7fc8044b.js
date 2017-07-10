define([ "siglasApp", "services/ItemService", "services/CategoryService" ], function(siglasApp) {
    siglasApp.controller("HomeCtrl", [ "$scope", "$rootScope", "$location", "ItemService", "CategoryService", function($scope, $rootScope, $location, ItemService, CategoryService) {
        console.log("HomeCtrl");
        var self = this;
        self.items = [], ItemService.mostSold().then(function(items) {
            self.items = items, self.compartments = _.reduce(self.items, function(memo, val, i) {
                var key = Math.floor(i / 6);
                return key in memo ? memo[key].push(val) : memo[key] = [ val ], memo;
            }, {});
        }, function(err) {
            console.error(err);
        }), CategoryService.tree().then(function(categories) {
            self.categories = categories;
        }, function(err) {
            console.error(err);
        });
    } ]);
});