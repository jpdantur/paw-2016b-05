$ document .ready ->
	$('[data-buyer-email]') .click (e) ->

		$ this .fadeOut "slow", ->
			$p = $("<pre><p class='lead' id='contact-email'>"+$ this .data('buyer-email') + "</p></pre>").hide!
			$ this .replaceWith $p
			$p .fadeIn "slow"