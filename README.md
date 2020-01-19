# flickr-test

Test app that consumes flickr photo search API.

Only library in use is Google Architecture Components Lifecycle Extensions & ViewModel.

Custom implementations for 
 * HTTP Client
 * JSON deserializer
 * Background thread handling
 * Image fetching
 
 
## Architecture and design choices

 The app is built using MVVM architecture.
 
 There is a single Activity (`MainActivity`) containing a `RecyclerView`
 
 Due to the size of the project, a ServiceLocator is used as a means of inversion of control. Dependencies are declared as Singletons and "injected" as needed.  
 
 All business logic is orchestrated from the `MainViewModel`. A request to search is pushed every time user types in more than 3 characters on the search bar. 
 
 `FlickrService` performs the actual request using the `HttpClient`, which returns the response as a String. This string is used by the Serializer to convert into an `ApiResponse` class.
 `FlickrService` works synchronously, `PhotoRepository` turns the work into a `Task`, which is passed on to a `TaskRunner`, which provide the ability to execute the job in a background thread using `ExecutorService`.
 The ViewModel will trigger the actual execution passing on a `Callback` object, which is called from the `mainThread` by using a `Handler` with `Looper.getMainLooper()`.
 
 The response object (a list of Photos) is wrapped in a `Resource` class which reflects the state of the work being done (`InProgress`,`Success`,`Failure`), this way we can act upon the different stages from the UI.
 
 `ImageFetcher` is called from the `ViewHolder` to retrieve images in the background. It works using `ExecutorService` and `Handlers` as well. The target size must be provided in the request call in order to resize the images to fit the `ImageViews`. Images are downloaded in the cache folder of the device.
 
 The project supports a minimum of API 19, which has been tested. It has been also tested in multiple devices including latest version of Android.

 
## Possible improvements

* Incorporate a memory cache to the image fetcher to speed up image retrieval
* In a bigger project, some sort of dependency injection would replace the `ServiceLocator`
* Only the bare minimum functions have been developed for `HttpClient` (just get reponses), `Serializer` (just deserializing), etc.
* If no size would be specified, `ImageFetcher` should calculate the size automatically based on the measured size of the `ImageView`. Thus the logic of bitmap decoding should be put after a callback confirming the view has been attached (so it has a width and height)
* Ideally the Serializer would be a generic one instead of specific for this API but due to time constraints it was done this way
* The current implementation uses a `Repository` class that actually acts as a `Use Case` (or domain layer in Clean Architecture). I thought including both layers would be overengineering for this small project so I decided to skip one of them. In a full fledged project, this layer would be there to implement the actual business logic while the `Repository` would just manage the data access in case there would be multiple data sources.


