define([ "siglasApp", "services/StoreService" ], function(siglasApp) {
    siglasApp.controller("AllCtrl", [ "$scope", "$rootScope", "$location", "$route", "toastr", "$filter", "StoreService", function($scope, $rootScope, $location, $route, toastr, $filter, StoreService) {
        function updateItemsQuery(delta) {
            _.extend(self.itemsQuery, delta), publishedItems();
        }
        function onItemsOrderChange() {
            var parts = self.itemsOrder.split("-");
            updateItemsQuery({
                pageNumber: 0,
                sortOrder: parts[1],
                sortField: parts[0]
            });
        }
        function publishedItems() {
            self.itemsLoading = !0, console.log(self.itemsQuery), StoreService.search(self.itemsQuery).then(function(result) {
                return console.log(result), self.itemResult = result, self.itemsLoading = !1, 1 === self.itemResult.results.length ? $location.search({}).path("/store/items/" + self.itemResult.results[0].id) : (self.displayedCategories = self.itemResult.selectedCategories, 
                void $location.search(self.itemsQuery));
            }, function(err) {
                console.error(err);
            });
        }
        function toggleCategory(category) {
            var idx = _.findIndex(self.selectedCategories, {
                id: category.id
            });
            ~idx ? self.selectedCategories.splice(idx, 1) : self.selectedCategories.push(category);
        }
        function applyCategory() {
            updateItemsQuery({
                pageNumber: 0,
                categories: _.map(self.selectedCategories, "id")
            }), self.selectedCategories = [];
        }
        console.log("AllCtrl"), $scope._ = _;
        var self = this;
        self.itemsQuery = _.extend({
            pageNumber: 0,
            pageSize: 20,
            query: "",
            sortOrder: "ASC",
            sortField: "PRICE",
            minPrice: null,
            maxPrice: null,
            categories: []
        }, _.mapValues($location.search(), function(val) {
            return val && isFinite(val) ? parseInt(val, 10) : val;
        })), self.displayedCategories = [], self.selectedCategories = [], console.log(self.itemsQuery), 
        self.itemsLoading = !1, self.itemResult = {}, self.itemsOrder = "PRICE-ASC", self.updateItemsQuery = _.debounce(updateItemsQuery, 800), 
        self.onItemsOrderChange = onItemsOrderChange, self.toggleCategory = toggleCategory, 
        self.applyCategory = applyCategory, $scope.$on("query.update", function(e, query) {
            console.log("query.updated", query), updateItemsQuery({
                pageNumber: 0,
                query: query
            });
        }), publishedItems();
    } ]);
});