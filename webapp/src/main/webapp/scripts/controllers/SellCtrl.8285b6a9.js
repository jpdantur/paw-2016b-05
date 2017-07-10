define([ "siglasApp", "services/CategoryService", "services/ItemService" ], function(siglasApp) {
    siglasApp.controller("SellCtrl", [ "$scope", "$rootScope", "$route", "$location", "toastr", "CategoryService", "ItemService", function($scope, $rootScope, $route, $location, toastr, CategoryService, ItemService) {
        function selectCategory(category, depth) {
            category.children.length ? (self.selectedCategory = null, self.categoryPath.splice(depth, self.categoryPath.length - depth, category.children)) : (self.selectedCategory = category, 
            self.categoryPath.splice(depth, self.categoryPath.length - depth)), self.highlighted = _.map(self.categoryPath, function(group) {
                return group[0].parent;
            });
        }
        function submit(valid) {
            valid && (console.log(self.item), _.extend(self.item, {
                category: self.selectedCategory.id
            }), ItemService.create(self.item).then(function(result) {
                console.log(result), toastr.success("Item successfully created"), $location.path("/store/sell/images/" + result.id);
            }, function(err) {
                console.error(err), toastr.error("An error ocurred. Please try again");
            }));
        }
        console.log("SellCtrl"), $scope._ = _, $scope.$location = $location;
        var self = this;
        self.categoryPath = [], self.highlighted = [], self.selectedCategory = null, self.item = {
            used: !1,
            itemStatus: "ACTIVE",
            description: ""
        }, self.selectCategory = selectCategory, self.submit = submit, CategoryService.tree().then(function(categories) {
            console.log(categories), self.categories = categories, self.categoryPath.push(self.categories);
        }, function(err) {
            console.error(err);
        });
    } ]);
});