
$ document .ready !->

	$window = $ window
	$navbar = $ \#navbar-content

	$ \#simple-search-form .submit (e) !->

		if $ .trim($ \#simple-search-input .val!) == ''
			e.preventDefault!

	$ \#link-busqueda-compleja .click (e) !->

		e.preventDefault!
		$target = $ \#busqueda-compleja

		$ "html,body"
		.animate do
			scrollTop: $target.offset!.top
			, 800
			, 'easeOutQuint'





	