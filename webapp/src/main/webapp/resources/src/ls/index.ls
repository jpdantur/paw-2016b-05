
$ document .ready !->

	$window = $ window
	$navbar = $ \#navbar-content


	$window .scroll (e) !->

		if !$navbar.hasClass(\scrolled) && $navbar.offset!.top > 50
			$navbar.addClass \scrolled

			$ \.navbar-brand .animate do
				'font-size': '1em'

		else if $navbar.hasClass(\scrolled) && $navbar.offset!.top <= 50
			$navbar .removeClass \scrolled

			$ \.navbar-brand .animate do
				'font-size': '2em'

	$ \#link-busqueda-compleja .click (e) !->

		e.preventDefault!
		$target = $ \#busqueda-compleja

		$ "html,body"
		.animate do
			scrollTop: $target.offset!.top
			, 800
			, 'easeOutQuint'

	$ \input .keydown (e) !->

		e.preventDefault!
		return false

	$ \input .bind 'cut copy pase', (e) !->
		e.preventDefault!

	$ \input .on 'contextmenu', (e) !->
		return false





	