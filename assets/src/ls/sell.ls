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

		$ \#submit-button .addClass \disabled

		if !$category-input .val! || $category-input .val! == '0'
			
			$ \#submit-button .removeClass \disabled

			$.notify {
				message: messages.sellPickCategory
			} , do
				type: 'warning'


			e.preventDefault!
			return false

	markActiveTree = !->

		$self = this

		$self. addClass \active

		$card = $self.closest \.category-card

		$parentLink = $ ".category-card a[data-id=#{$card.data('parent')}]"

		$parentCard = $parentLink .parent! .addClass \active .closest \.category-card

		$card .addClass \visible

		if $card.data('parent') != $parentCard .data('parent')
			markActiveTree.apply $parentLink

	$activeCategory = $ ".category-card a[data-id=#{$category-input.val!}]"

	$success-card.addClass \visible
	$category-name.text $activeCategory.text!

	markActiveTree.apply(  $activeCategory.parent! )

	# markActiveTree.apply( $ ".category-card a[data-id=#{$category-input.val!}]" .parent!)


		