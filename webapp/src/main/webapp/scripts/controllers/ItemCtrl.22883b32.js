define([ "siglasApp", "services/ItemService", "services/FavouritesService", "services/IdService", "services/StoreService", "directives/star-rating" ], function(siglasApp) {
    siglasApp.controller("ItemCtrl", [ "$scope", "$rootScope", "$location", "$route", "$q", "toastr", "ItemService", "FavouritesService", "IdService", "StoreService", "$filter", function($scope, $rootScope, $location, $route, $q, toastr, ItemService, FavouritesService, IdService, StoreService, $filter) {
        function postComment(valid) {
            valid && (console.log(self.comment), ItemService.addComment(self.storeItem.id, self.comment).then(function(result) {
                console.log(result), toastr.success($filter("translate")("ng.messages.reviewSuccess")), 
                self.storeItem.comments.unshift(result);
            }, function(err) {
                toastr.error($filter("translate")("ng.messages.reviewError")), console.error(err);
            }));
        }
        function addFavourite(id) {
            FavouritesService.add(id).then(function(result) {
                $rootScope.loggedUser.favourites.hasMore || $rootScope.loggedUser.favourites.push(result), 
                self.isFavourite = result, toastr.success($filter("translate")("successMessages.toggleFavourite.addSuccess"));
            }, function(err) {
                toastr.error($filter("translate")("successMessages.toggleFavourite.addError"));
            });
        }
        function purchase(item) {
            self.purchasing = !0, StoreService.purchase(item).then(function(result) {
                console.log(result), toastr.success($filter("translate")("successMessages.buyItem.success")), 
                self.isPurchased = result;
            }, function(err) {
                self.purchasing = !1, toastr.error($filter("translate")("successMessages.buyItem.error")), 
                console.error(err);
            });
        }
        console.log("ItemCtrl"), console.log($route), $scope._ = _, $scope.$location = $location;
        var id = $route.current.pathParams.id, self = this;
        self.isFavourite = null, self.isPublished = null, self.isPurchased = null, self.related = null, 
        self.comment = {
            rating: 2.5
        }, self.owner = {
            sellerRating: 2.5
        }, self.postComment = postComment, self.addFavourite = addFavourite, self.purchase = purchase, 
        $q.all({
            item: ItemService.findById(id),
            favourites: $rootScope.loggedUser ? FavouritesService.all() : Promise.resolve([]),
            published: $rootScope.loggedUser ? ItemService.allPublished() : Promise.resolve([]),
            purchased: $rootScope.loggedUser ? ItemService.pendingPurchases() : Promise.resolve([]),
            comments: ItemService.comments(id),
            related: ItemService.related(id, {
                pageNumber: 0,
                pageSize: 4
            })
        }).then(function(results) {
            return self.storeItem = results.item, _.includes([ "UNPUBLISHED", "PAUSED" ], self.storeItem.status) && $rootScope.loggedUser.id !== self.storeItem.owner.id ? (console.log("403"), 
            $location.path("/403")) : ("UNPUBLISHED" === self.storeItem.status && toastr.warning($filter("translate")("item.notifications.draft.message"), $filter("translate")("item.notifications.draft.title"), {
                onTap: function(toast) {
                    $location.path("/store/item/" + self.storeItem.id + "/details");
                },
                tapToDismiss: !0
            }), "PAUSED" === self.storeItem.status && (toastr.warning($filter("translate")("item.notifications.paused.alternative"), {
                onTap: function(toast) {
                    $location.path("/store/item/" + self.storeItem.id + "/details");
                },
                tapToDismiss: !0
            }), toastr.warning($filter("translate")("item.notifications.paused.message"), $filter("translate")("item.notifications.paused.title"), {
                onTap: function(toast) {
                    $location.path("/profile/details").search({
                        tab: "items"
                    });
                },
                tapToDismiss: !0
            })), self.isFavourite = _.find(results.favourites, function(fav) {
                return fav.item.id === self.storeItem.id;
            }), self.isPublished = _.find(results.published, function(i) {
                return i.id === self.storeItem.id;
            }), self.isPurchased = _.find(results.purchased, function(p) {
                return p.id === self.storeItem.id;
            }), self.storeItem.comments = results.comments, self.related = results.related.results, 
            void IdService.profile(self.storeItem.owner.username).then(function(result) {
                self.owner = result;
            }, function(err) {
                console.error(err);
            }));
        }, function(err) {
            console.error(err), 401 === err.status && $location.path("/403");
        }), $scope.$on("fav.remove", function(e, id) {
            self.isFavourite && self.isFavourite.id === id && (self.isFavourite = null);
        });
    } ]);
});