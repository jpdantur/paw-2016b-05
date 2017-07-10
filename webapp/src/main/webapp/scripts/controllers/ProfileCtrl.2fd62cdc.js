define([ "siglasApp", "services/UserService", "services/ItemService", "services/SalesService", "services/PurchasesService", "services/FavouritesService", "directives/password-check", "ngBootbox" ], function(siglasApp) {
    siglasApp.controller("ProfileCtrl", [ "$scope", "$rootScope", "$route", "$location", "$ngBootbox", "toastr", "UserService", "ItemService", "SalesService", "PurchasesService", "FavouritesService", "$filter", function($scope, $rootScope, $route, $location, $ngBootbox, toastr, UserService, ItemService, SalesService, PurchasesService, FavouritesService, $filter) {
        function switchToTab(tab) {
            $location.search({
                tab: tab
            }), self.selectedTab = tab;
        }
        function changeEmail(isValid) {
            console.log(self.user), console.log($rootScope.loggedUser.email), isValid && UserService.update(self.user).then(function(user) {
                console.log(user), $rootScope.loggedUser.email = user.email, toastr.success($filter("translate")("ng.messages.emailSuccess"));
            }, function(err) {
                toastr.error($filter("translate")("ng.messages.emailError"));
            });
        }
        function changePassword(isValid) {
            isValid && UserService.changePassword(self.pass).then(function() {
                toastr.success($filter("translate")("ng.messages.passwordSuccess"));
            }, function(err) {
                toastr.error($filter("translate")("ng.messages.passwordError"));
            });
        }
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
            self.itemsLoading = !0, ItemService.published(self.itemsQuery).then(function(result) {
                console.log(result), self.itemResult = result, self.itemsLoading = !1;
            }, function(err) {
                console.error(err);
            });
        }
        function setPublicationStatus(originalItem, status) {
            var i = _.extend({}, originalItem, {
                category: originalItem.category.id,
                owner: originalItem.owner.id,
                status: status
            });
            console.log(i), ItemService.update(i).then(function(item) {
                console.log(item), angular.extend(originalItem, item), toastr.success($filter("translate")("ng.messages.itemSuccess"));
            }, function(err) {
                console.error(err), toastr.error($filter("translate")("ng.messages.itemError"));
            });
        }
        function updateSalesQuery(delta) {
            _.extend(self.salesQuery, delta), sales();
        }
        function onSalesOrderChange() {
            var parts = self.salesOrder.split("-");
            updateSalesQuery({
                pageNumber: 0,
                sortOrder: parts[1],
                sortField: parts[0]
            });
        }
        function sales() {
            self.salesLoading = !0, SalesService.mine(self.salesQuery).then(function(result) {
                console.log(result), self.salesResult = result, self.salesLoading = !1;
            }, function(err) {
                console.error(err);
            });
        }
        function approveSale(item) {
            SalesService.approve(item.id).then(function(result) {
                console.log(result), item.status = "APPROVED", toastr.success($filter("translate")("ng.messages.purchaseSuccess"));
            }, function(err) {
                console.error(err), toastr.error($filter("translate")("ng.messages.purchaseError"));
            });
        }
        function declineSale(item) {
            SalesService.decline(item.id).then(function(result) {
                console.log(result), item.status = "DECLINED", toastr.success($filter("translate")("ng.messages.purchaseSuccess2"));
            }, function(err) {
                console.error(err), toastr.success($filter("translate")("ng.messages.purchaseError2"));
            });
        }
        function showScoresAsSeller(transaction) {
            $location.search(angular.extend({}, $route.current.params, {
                txId: transaction.id,
                role: "seller"
            }));
        }
        function updatePurchasesQuery(delta) {
            _.extend(self.purchasesQuery, delta), purchases();
        }
        function onPurchasesOrderChange() {
            var parts = self.purchasesOrder.split("-");
            updatePurchasesQuery({
                pageNumber: 0,
                sortOrder: parts[1],
                sortField: parts[0]
            });
        }
        function purchases() {
            self.purchasesLoading = !0, PurchasesService.mine(self.purchasesQuery).then(function(result) {
                console.log(result), self.purchasesResult = result, self.purchasesLoading = !1;
            }, function(err) {
                console.error(err);
            });
        }
        function updateFavouritesQuery(delta) {
            _.extend(self.favouritesQuery, delta), favourites();
        }
        function onFavouritesOrderChange() {
            var parts = self.favouritesOrder.split("-");
            updateFavouritesQuery({
                pageNumber: 0,
                sortOrder: parts[1],
                sortField: parts[0]
            });
        }
        function favourites() {
            self.favouritesLoading = !0, FavouritesService.mine(self.favouritesQuery).then(function(result) {
                console.log(result), self.favouritesResult = result, self.favouritesLoading = !1;
            }, function(err) {
                console.error(err);
            });
        }
        function removeFavourite(favId) {
            FavouritesService.remove(favId).then(function(result) {
                self.favouritesResult.results = _.filter(self.favouritesResult.results, function(fav) {
                    return fav.id !== favId;
                }), $rootScope.loggedUser.favourites = _.filter($rootScope.loggedUser.favourites, function(fav) {
                    return fav.id !== favId;
                }), $rootScope.loggedUser.favourites.hasMore = $rootScope.loggedUser.favourites.length > 8, 
                toastr.success($filter("translate")("successMessages.toggleFavourite.addSuccess"));
            }, function(err) {
                console.error(err), toastr.error($filter("translate")("successMessages.toggleFavourite.addError"));
            });
        }
        console.log("ProfileCtrl"), $scope.Math = window.Math;
        var self = this;
        console.log($route), self.selectedTab = $route.current.params.tab || "account", 
        self.switchToTab = switchToTab, self.user = {
            firstName: $rootScope.loggedUser.firstName,
            lastName: $rootScope.loggedUser.lastName,
            email: $rootScope.loggedUser.email
        }, self.pass = {}, self.itemsQuery = {
            pageNumber: 0,
            pageSize: 20,
            query: "",
            status: "ANY",
            sortOrder: "ASC",
            sortField: "PRICE"
        }, self.itemsLoading = !1, self.itemResult = {}, self.itemsOrder = "PRICE-ASC", 
        self.salesQuery = {
            pageNumber: 0,
            pageSize: 20,
            query: "",
            status: "PENDING",
            sortOrder: "ASC",
            sortField: "PRICE"
        }, self.salesLoading = !1, self.salesResult = {}, self.salesOrder = "PRICE-ASC", 
        self.approveSale = approveSale, self.declineSale = declineSale, self.showScoresAsSeller = showScoresAsSeller, 
        self.purchasesQuery = {
            pageNumber: 0,
            pageSize: 20,
            query: "",
            status: "PENDING",
            sortOrder: "ASC",
            sortField: "PRICE"
        }, self.purchasesLoading = !1, self.purchasesResult = {}, self.purchasesOrder = "PRICE-ASC", 
        self.favouritesQuery = {
            pageNumber: 0,
            pageSize: 20,
            query: "",
            sortOrder: "ASC",
            sortField: "PRICE"
        }, self.favouritesLoading = !1, self.favouritesResult = {}, self.favouritesOrder = "PRICE-ASC", 
        self.removeFavourite = removeFavourite, self.changeEmail = changeEmail, self.changePassword = changePassword, 
        self.publishedItems = publishedItems, self.updateItemsQuery = _.debounce(updateItemsQuery, 400), 
        self.onItemsOrderChange = onItemsOrderChange, self.setPublicationStatus = setPublicationStatus, 
        self.updateSalesQuery = _.debounce(updateSalesQuery, 400), self.onSalesOrderChange = onSalesOrderChange, 
        self.updatePurchasesQuery = _.debounce(updatePurchasesQuery, 400), self.onPurchasesOrderChange = onPurchasesOrderChange, 
        self.updateFavouritesQuery = _.debounce(updateFavouritesQuery, 400), self.onFavouritesOrderChange = onFavouritesOrderChange, 
        publishedItems(), sales(), purchases(), favourites(), $scope.$on("fav.remove", function(e, id) {
            console.log("fav.remove"), self.favouritesResult.results = _.filter(self.favouritesResult.results, function(fav) {
                return fav.id !== id;
            });
        });
    } ]);
});