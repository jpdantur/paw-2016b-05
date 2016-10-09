$ document .ready ->

	$ \.add-favourite .click (e) !->

		$self = $ this

		item = $self .data \item

		remove = '/remove'
		if $self.hasClass \text-muted
			remove = '/'

		$ .ajax do
			url: baseUrl + '/store/item/' + item + '/favourite' + remove
			type: 'POST'
			success: (data) !->
				console.log data
				if !data.err
					$self .toggleClass 'text-muted text-danger'

	$('[data-buyer-email]') .click (e) ->

		$ this .fadeOut "slow", ->
			$p = $("<pre><p class='lead' id='contact-email'>"+$ this .data('buyer-email') + "</p></pre>").hide!
			$ this .replaceWith $p
			$p .fadeIn "slow"