define([ "siglasApp" ], function(siglasApp) {
    siglasApp.directive("starRating", function() {
        return {
            restrict: "EA",
            template: '<ul class="star-rating" ng-class="{readonly: readonly}">  <li ng-repeat="star in stars" class="star" ng-class="{filled: star.filled}" ng-click="toggle($index)">    <i class="fa fa-star"></i>  </li></ul>',
            scope: {
                ratingValue: "=ngModel",
                max: "=?",
                onRatingSelect: "&?",
                readonly: "=?"
            },
            link: function(scope, element, attributes) {
                function updateStars() {
                    scope.stars = [];
                    for (var i = 0; i < scope.max; i++) scope.stars.push({
                        filled: i < scope.ratingValue
                    });
                }
                void 0 === scope.max && (scope.max = 5), scope.toggle = function(index) {
                    void 0 !== scope.readonly && scope.readonly !== !1 || (scope.ratingValue = index + 1, 
                    scope.onRatingSelect && scope.onRatingSelect({
                        rating: index + 1
                    }));
                }, scope.$watch("ratingValue", function(oldValue, newValue) {
                    (newValue || 0 === newValue) && updateStars();
                });
            }
        };
    });
});