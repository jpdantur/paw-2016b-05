+function($) {
    "use strict";
    function Plugin(option) {
        return this.each(function() {
            var $this = $(this), data = $this.data("bs.tab");
            data || $this.data("bs.tab", data = new Tab(this)), "string" == typeof option && data[option]();
        });
    }
    var Tab = function(element) {
        this.element = $(element);
    };
    Tab.VERSION = "3.3.7", Tab.TRANSITION_DURATION = 150, Tab.prototype.show = function() {
        var $this = this.element, $ul = $this.closest("ul:not(.dropdown-menu)"), selector = $this.data("target");
        if (selector || (selector = $this.attr("href"), selector = selector && selector.replace(/.*(?=#[^\s]*$)/, "")), 
        !$this.parent("li").hasClass("active")) {
            var $previous = $ul.find(".active:last a"), hideEvent = $.Event("hide.bs.tab", {
                relatedTarget: $this[0]
            }), showEvent = $.Event("show.bs.tab", {
                relatedTarget: $previous[0]
            });
            if ($previous.trigger(hideEvent), $this.trigger(showEvent), !showEvent.isDefaultPrevented() && !hideEvent.isDefaultPrevented()) {
                var $target = $(selector);
                this.activate($this.closest("li"), $ul), this.activate($target, $target.parent(), function() {
                    $previous.trigger({
                        type: "hidden.bs.tab",
                        relatedTarget: $this[0]
                    }), $this.trigger({
                        type: "shown.bs.tab",
                        relatedTarget: $previous[0]
                    });
                });
            }
        }
    }, Tab.prototype.activate = function(element, container, callback) {
        function next() {
            $active.removeClass("active").find("> .dropdown-menu > .active").removeClass("active").end().find('[data-toggle="tab"]').attr("aria-expanded", !1), 
            element.addClass("active").find('[data-toggle="tab"]').attr("aria-expanded", !0), 
            transition ? (element[0].offsetWidth, element.addClass("in")) : element.removeClass("fade"), 
            element.parent(".dropdown-menu").length && element.closest("li.dropdown").addClass("active").end().find('[data-toggle="tab"]').attr("aria-expanded", !0), 
            callback && callback();
        }
        var $active = container.find("> .active"), transition = callback && $.support.transition && ($active.length && $active.hasClass("fade") || !!container.find("> .fade").length);
        $active.length && transition ? $active.one("bsTransitionEnd", next).emulateTransitionEnd(Tab.TRANSITION_DURATION) : next(), 
        $active.removeClass("in");
    };
    var old = $.fn.tab;
    $.fn.tab = Plugin, $.fn.tab.Constructor = Tab, $.fn.tab.noConflict = function() {
        return $.fn.tab = old, this;
    };
    var clickHandler = function(e) {
        e.preventDefault(), Plugin.call($(this), "show");
    };
    $(document).on("click.bs.tab.data-api", '[data-toggle="tab"]', clickHandler).on("click.bs.tab.data-api", '[data-toggle="pill"]', clickHandler);
}(jQuery);