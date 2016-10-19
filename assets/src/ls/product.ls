$ document .ready !->

	$ \.add-favourite .click (e) !->

		$self = $ this

		item = $self .data \item

		remove = '/remove'
		isRemoving = true
		if $self.hasClass \text-muted
			remove = '/'
			isRemoving = false

		$ .ajax do
			url: baseUrl + '/store/item/' + item + '/favourite' + remove
			type: 'POST'
			success: (data) !->
				console.log data
				if data.err
					$.notify {
						message: if isRemoving then messages.removeError else messages.addError
					} , do
						type: 'danger'
				else
					$.notify {
						message: if isRemoving then messages.removeSuccess else messages.addSuccess
					} , do
						type: 'success'
					$self .toggleClass 'text-muted text-danger'

	$('[data-buyer-email]') .click (e) !->

		e.preventDefault!

		

	$ \#purchase .click (e) !->

		e.preventDefault!

		$self = $ this

		item = $self .data \item

		$ .ajax do
			url: baseUrl + '/store/item/' + item + '/purchase'
			type: 'POST'
			success: (data) !->
				if data.err
					$.notify {
						message: 'No se pudo comprar el producto'
					} , do
						type: 'danger'
				else
					$.notify {
						message: 'Enhorabuena! La compra se ha realizado con exito'
					} , do
						type: 'success'

					$self .fadeOut "slow", ->
						$p = $("<pre><p class='lead' id='contact-email'>"+ $self .data('buyer-email') + "</p></pre>").hide!
						$self .replaceWith $p
						$p .fadeIn "slow"


