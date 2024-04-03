# MyFancyBlog

### - Run all tests
`mvn clean verify` will compile and execute all tests of the project

### - Run application from terminal
`mvn spring-boot:run` will run the entire application on port 8090.

### Operations

## - Create
  Allows to create a new blog post, for both `admin` and `user` users, as from the requests. Response codes are:
- `201` when the post has been created succesfully
- `400` when the post length exceeds the limit (1024 chars)
- `401` when the user is not recognized 
- `500` if an error occurs (e.g. same post has been already published)
```
POST http://localhost:8090/my-fancy-blog/v1/posts/create/
Content-Type: application/json
X-User: user

{
"title": "String",
"content": "One morning, when Gregor Samsa woke from troubled dreams, he found himself transformed in his bed into a horrible vermin. He lay on his armour-like back, and if he lifted his head a little he could see his brown belly, slightly domed and divided by arches into stiff sections. The bedding was hardly able to cover it and seemed ready to slide off any moment. His many legs, pitifully thin compared with the size of the rest of him, waved about helplessly as he looked. "What's happened to me?" he thought. It wasn't a dream. His room, a proper human room although a little too small, lay peacefully between its four familiar walls. A collection of textile samples lay spread out on the table - Samsa was a travelling salesman - and above it there hung a picture that he had recently cut out of an illustrated magazine and housed in a nice, gilded frame. It showed a lady fitted out with a fur hat and fur boa who sat upright",
"author": "String",
"image": "String",
"category": "String",
"tags": [
"somestring1",
"somestring2"

}
```
## - Retrieve (by id)
Allows to retrieve a post by its it. 

- Return 404 if no post has been found matching the given id
- Return 200 with the retrieved posts found

```
GET http://localhost:8090/my-fancy-blog/v1/posts/?id=1
X-User: user
```


## - Search
````
GET http://localhost:8090/my-fancy-blog/v1/posts/?title=String
X-User: user
````
  Allows to search a blog post, by its title, category or tags. Results could be 0...N, depending on the criteria and matching posts.
- Return 400 if no one parameter has been passed to the controller
- Return 401 if user is not enabled
- Return 404 if no post has been found matching the criteria
- Return 200 with the retrieved posts found


## - Delete
Allows to delete a post by id.

```
DELETE http://localhost:8090/my-fancy-blog/v1/posts/?id=1
X-User: admin
```
- Return 401 if user is not enabled
- Return 404 if the requested post has not been found
- Return 204 if the requested post has been deleted

## - Update
### - Full Update
Both the body and id (as query param) are mandatory

- Return 401 if uses is unknown
- Return 200 if post has been fully updated
- Return 400 if try to update full post but content is too long
- Return 500 if update goes in error

```
PUT http://localhost:8090/my-fancy-blog/v1/posts/update/full?id=1
Content-Type: application/json
X-User: user

{
  "title": "digit",
  "content": "One morning, when Gregor Samsa woke from troubled dreams",
  "author": "String",
  "image": "picasso",
  "category": "String",
  "tags": [
    "nostring",
    "somestring2"
  ]
}
```

### - Partial Update 
Allows to update a blog post, specifying id as mandatory query param and all the other fields are optional
(title: String, content: String, author: String, image: String, category: String, tags: List<String>)

- Return 401 if uses is unknown
- Return 200 if post has been fully updated
- Return 400 if try to update full post but content is too long
- Return 500 if update goes in error


```
PUT http://localhost:8090/my-fancy-blog/v1/posts/update-category/?id=1&category=abc
Content-Type: application/json
X-User: admin
```

### - Category Update
Allows to update the category of a blog post, specifying id and category as mandatory query params

- Return 401 if uses is unknown
- Return 200 if post has been fully updated
- Return 400 if try to update full post but content is too long
- Return 500 if update goes in error


```
PUT http://localhost:8090/my-fancy-blog/v1/posts/update-category/?id=1&category=abc
Content-Type: application/json
X-User: admin
```

### Decisions

- For sake of simplicity, the repository has implemented as an in memory map, with an interface, which allows to implement another repository to make the same operations with any kind of storage.
- The operation to assign a category to a post, it was understood as a category update of a blog post.


