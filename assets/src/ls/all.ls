$ document .ready !->

	$ \.rating.static .rate do
		max_value: 5,
		step_size: 0.5,
		readonly: true

	$ \.remove-fav .click (e) !->

		e.preventDefault!

		$self = $ this

		if $self.hasClass \disabled
			return

		item = $self .data \id

		$self .addClass \disabled

		$ .ajax do
			url: baseUrl + '/favourites/remove/' + item
			type: 'POST'
			success: (data) !->
				$self .removeClass \disabled
				console.log data
				if data.err
					$.notify {
						message: globalMessages.removeError
					} , do
						type: 'danger'
				else
					$.notify {
						message: globalMessages.removeSuccess
					} , do
						type: 'success'
					$self .closest 'li,tr' .remove!
					