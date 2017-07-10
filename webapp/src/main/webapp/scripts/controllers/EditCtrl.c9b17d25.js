define([ "siglasApp", "services/ItemService", "services/CategoryService" ], function(siglasApp) {
    siglasApp.controller("EditCtrl", [ "$scope", "$rootScope", "$location", "$q", "$route", "ItemService", "CategoryService", "toastr", function($scope, $rootScope, $location, $q, $route, ItemService, CategoryService, toastr) {
        function itemCategoryLookup(categories, depth) {
            _.each(categories, function(category) {
                if (!stop) {
                    if (category.id === self.item.category.id) return stop = !0, selectCategory(category, depth);
                    selectCategory(category, depth), itemCategoryLookup(category.children, depth + 1);
                }
            });
        }
        function switchToTab(tab) {
            $location.search({
                tab: tab
            }), self.selectedTab = tab;
        }
        function selectCategory(category, depth) {
            category.children.length ? (self.selectedCategory = null, self.categoryPath.splice(depth, self.categoryPath.length - depth, category.children)) : (self.selectedCategory = category, 
            self.categoryPath.splice(depth, self.categoryPath.length - depth)), self.highlighted = _.map(self.categoryPath, function(group) {
                return group[0].parent;
            });
        }
        function submit(valid) {
            valid && (_.extend(self.item, {
                category: self.selectedCategory.id
            }), console.log(self.item), ItemService.update(self.item).then(function(result) {
                console.log(result), toastr.success($filter("translate")("ng.messages.editSuccess"));
            }, function(err) {
                console.error(err), toastr.error($filter("translate")("ng.messages.editError"));
            }));
        }
        console.log("EditCtrl");
        var self = this;
        $scope._ = _, self.selectedTab = $route.current.params.tab || "details", self.switchToTab = switchToTab, 
        self.item = null, self.categoryPath = [], self.highlighted = [], self.selectedCategory = null, 
        self.selectCategory = selectCategory, self.submit = submit, $q.all({
            item: ItemService.findById($route.current.pathParams.id),
            category: CategoryService.tree()
        }).then(function(result) {
            self.item = result.item, self.categories = result.category, self.categoryPath.push(self.categories), 
            itemCategoryLookup(self.categories, 1), console.log(self.selectedCategory);
        }, function(err) {
            console.error(err);
        });
        var stop = !1;
    } ]);
});