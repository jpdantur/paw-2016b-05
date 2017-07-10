+function($) {
    "use strict";
    function Plugin(option) {
        return this.each(function() {
            var $this = $(this), data = $this.data("bs.alert");
            data || $this.data("bs.alert", data = new Alert(this)), "string" == typeof option && data[option].call($this);
        });
    }
    var dismiss = '[data-dismiss="alert"]', Alert = function(el) {
        $(el).on("click", dismiss, this.close);
    };
    Alert.VERSION = "3.3.7", Alert.TRANSITION_DURATION = 150, Alert.prototype.close = function(e) {
        function removeElement() {
            $parent.detach().trigger("closed.bs.alert").remove();
        }
        var $this = $(this), selector = $this.attr("data-target");
        selector || (selector = $this.attr("href"), selector = selector && selector.replace(/.*(?=#[^\s]*$)/, ""));
        var $parent = $("#" === selector ? [] : selector);
        e && e.preventDefault(), $parent.length || ($parent = $this.closest(".alert")), 
        $parent.trigger(e = $.Event("close.bs.alert")), e.isDefaultPrevented() || ($parent.removeClass("in"), 
        $.support.transition && $parent.hasClass("fade") ? $parent.one("bsTransitionEnd", removeElement).emulateTransitionEnd(Alert.TRANSITION_DURATION) : removeElement());
    };
    var old = $.fn.alert;
    $.fn.alert = Plugin, $.fn.alert.Constructor = Alert, $.fn.alert.noConflict = function() {
        return $.fn.alert = old, this;
    }, $(document).on("click.bs.alert.data-api", dismiss, Alert.prototype.close);
}(jQuery);