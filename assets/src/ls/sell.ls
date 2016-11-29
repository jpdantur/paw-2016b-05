$ document .ready !->

	$prev-card = []

	$success-card = $ \#success-card
	$category-name = $ \#category-name
	$category-input = $ \#category-input

	redrawBrowser = !->
		$visibleCards = $ '#category-browser .category-card.visible'

		$widths = $visibleCards .map ->
				$ this .width!
		
		widths = $widths.get!

		reducer = (memo, num) ->
			memo+num

		width = _.reduce(widths, reducer, 0)

		$ '#category-browser > div' .css do
			width: width + 20*widths.length

	$ '.category-card a' .click (e) !->
		e.preventDefault!

		$self = $ this
		$card = $self .closest \.category-card

		$next-card = $ ".category-card[data-parent=#{$self.data('id')}]"

		$card .nextAll \.category-card .removeClass \visible .find \.active .removeClass \active

		$self.parent! .siblings! .removeClass \active

		$self.parent!.addClass \active

		if $next-card .length == 0
			$success-card .addClass \visible
			$category-name .text( $self.text! )
			$category-input .val $self.data('id')
		else
			$success-card .removeClass \visible
			$next-card .addClass \visible

		redrawBrowser!
		
	$ 'form' .submit (e) !->
		
		console.log('form-submit')

		console.log($category-input .val!)
		console.log(!$category-input .val!)

		if !$category-input .val!
			console.log('preventing')
			e.preventDefault!
			return false


		