# Notes from Bogdan:
* All `networking` has been extracted to core module - intention is for this module to provide all the necessary data back to HostApp
* `core` module is using Content Provider to set itself up.
* Initially went down the route of implementing a local OkHttpClient in order to use retrofit and 
  potentially provide the functionality of switching between an actual network interface or local stub.
  However, I spent too long and ended up scraping that approach.
* It wasn't clear to me if the individual cards in the recycler view should show only the coins that are available in the wallet or not.

Things to improve:
* Make better of Single returns from the rxJava interface - at the moment the implementation in the MainFragmentViewModel is quite janky
* Ran out of time to actually make the UI more functional - for now the only thing the app does is to build the recycler view with the coins available in the wallet and their respective amount in both crypto amount and fiat.
* No unit tests - ran out of time.
* Have not double checked the maths.
