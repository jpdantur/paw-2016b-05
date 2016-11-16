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
		favId = $self.data \favid

		isRemoving = $self.hasClass \text-danger

		url = baseUrl + "/favourites/#{if isRemoving then 'remove' else 'add'}/#{if isRemoving then favId else item}"

		$self.prop \disabled, \disabled

		console.log(url)

		$ .ajax do
			url: url
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
					if !isRemoving
						$self.data \favid, data.favid

					

	$ '#purchase[data-buyer-email]' .click _.once (e) !->

		e.preventDefault!

		$self = $ this

		item = $self .data \item

		$self.prop \disabled, \disabled

		$ .ajax do
			url: baseUrl + '/store/item/' + item + '/purchase'
			type: 'POST'
			success: (data) !->
				
				$self.removeAttr \disabled

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
						$p = $("<div class='alert alert-info'><p>#{messages.purchaseInRevision}</p><a href='#{baseUrl}/profile/purchases' class='btn btn-success btn-sm'>#{messages.seeStatus}</a></div>").hide!
						$self .replaceWith $p
						$p .fadeIn "slow"

	$ \#comment-form .submit (e) !->

		console.log('hola')
		$ \#submit-comment .prop \disabled, \disabled

