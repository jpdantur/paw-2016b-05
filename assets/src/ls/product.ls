$ document .ready !->

	$ \.rating.static .rate do
		max_value: 5,
		step_size: 0.5,
		readonly: true

	$ \#score .rate do
		max_value: 5,
		step_size: 0.5,
		update_input_field_name: $('#score-input')

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

	$ '#purchase[data-buyer-email]' .click (e) !->

		e.preventDefault!

		$self = $ this

		item = $self .data \item

		$ .ajax do
			url: baseUrl + '/store/item/' + item + '/purchase'
			type: 'POST'
			success: (data) !->
				if data.err
					$.notify {
						message: messages.buyError
					} , do
						type: 'danger'
				else
					$.notify {
						message: messages.buySuccess
					} , do
						type: 'success'

					$self .fadeOut "slow", ->
						$p = $("<pre><p class='lead' id='contact-email'>"+ $self .data('buyer-email') + "</p></pre>").hide!
						$self .replaceWith $p
						$p .fadeIn "slow"


