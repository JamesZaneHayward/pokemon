# Pokemon App
Using the free https://pokeapi.co/ api to create a "Who's that Pokémon?" app inspired by the show's iconic advert break guessing game. Also includes a Pokédex screen to allow users to find more information on all their favourite pokémon. 

# Tech stack 
okhttp, retrofit, moshi for api calls and json data handling

compose, compose navigation, coil for ui and image loading

hilt for dependency injection

# Testing strategy
Unit testing examples can be found in the Domain package, covering some of the business logic of the UseCases. These use JUnit5 and Mockk. 

UI tests are setup to use JUnit4 and compose testing features. An example for the Who's That Pokémon? screen has been included. 

# Next steps
### Major concerns
- Error handling needs to be added - app is currently only focused on "happy paths" and requires relevant error handling and supporting tests
- Database to be added - app is hugely inefficient making the same api calls repeatedly when we largely don't expect the bulk of the data to change. Adding a database and pulling from there will cut down on the network calls, allow offline functionality, and decrease the startup time of the app on subsequent openings/game creations. 

### Other concerns
- App icon
- Splash screen 

### Nice to haves & additional features
- Expand support beyond initial 151 (first generation) pokémon.
- Rework Type to display the ingame type icons instead of text-only representations
- Allowing deeplinking to specific pokémon in the pokédex, particularly from the Who's that Pokémon? game screen.
- Searching/filtering of pokédex entries
- Persisting game win/loss record

# Screenshots
![Who's that pokemon? game screen](/assets/ss1.PNG)
![Who's that pokemon? post guess](/assets/ss2.PNG)
![Pokedex screen](/assets/ss3.PNG)
