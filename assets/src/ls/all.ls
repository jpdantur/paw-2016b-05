$ document .ready !->

	$ \.remove-fav .click (e) !->

		e.preventDefault!

		$self = $ this

		item = $self .data \id

		$ .ajax do
			url: baseUrl + '/store/item/' + item + '/favourite/remove'
			type: 'POST'
			success: (data) !->
				console.log data
				if !data.err
					$self .closest 'li' .remove!