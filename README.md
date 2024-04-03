# MyFancyBlog

### - Run all tests

`mvn clean verify` will compile and execute all tests of the project

### - Run application from terminal
#### - Run with docker
 - `docker build -t idell/blog .`
 - `docker run -p 8090:8090 idell/blog`
### - Run with maven
 - `mvn spring-boot:run` will run the entire application on port 8090.

## Operations

### - Create

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
"title": "Unraveling the Mysteries of Quantum Computing",
"content": "Quantum computing, with its promise of exponential speed, stands at the forefront of technological advancement. While challenges persist, breakthroughs in algorithms and hardware propel us closer to quantum supremacy. Yet, ethical considerations and societal impacts loom large. Collaboration among stakeholders is crucial as we navigate this uncharted territory, ensuring equitable benefits and responsible deployment. Quantum computing heralds a future where computation knows no bounds.",
"author": "ChatGPT",
"image": "https://www.example.com/images/sample.jpg",
"category": "Tech Insights",
"tags": [
"#QuantumComputing",
"#FutureTech"
]
}
```

### - Retrieve (by id)

Allows to retrieve a post by its it.

- Return `404` if no post has been found matching the given id
- Return `200` with the retrieved posts found

```
GET http://localhost:8090/my-fancy-blog/v1/posts/{{id}}
X-User: user
```

### - Search

````
GET http://localhost:8090/my-fancy-blog/v1/posts/?title="Journey into the Quantum Realm: Exploring the Future of Computing"
X-User: user
````

Allows to search a blog post, by its title, category or tags. Results could be 0...N, depending on the criteria and
matching posts.

- Return `400` if no one parameter has been passed to the controller
- Return `401` if user is not enabled
- Return `404` if no post has been found matching the criteria
- Return `200` with the retrieved posts found

### - Delete

Allows to delete a post by {{id}}.

```
DELETE http://localhost:8090/my-fancy-blog/v1/posts/{{id}}
X-User: admin
```

- Return `401` if user is not enabled
- Return `404` if the requested post has not been found
- Return `204` if the requested post has been deleted

### - Update

#### - Full Update
Allows to update the blog post identified by {{id}} 

- Return `401` if uses is unknown
- Return `200` if post has been fully updated
- Return `400` if try to update full post but content is too long
- Return `500` if update goes in error

```
PUT http://localhost:8090/my-fancy-blog/v1/posts/{{id}}/update/full/
Content-Type: application/json
X-User: user

{
"title": "Unraveling the Mysteries of Quantum Computing",
"content": "Quantum computing, with its promise of exponential speed, stands at the forefront of technological advancement. While challenges persist, breakthroughs in algorithms and hardware propel us closer to quantum supremacy. Yet, ethical considerations and societal impacts loom large. Collaboration among stakeholders is crucial as we navigate this uncharted territory, ensuring equitable benefits and responsible deployment. Quantum computing heralds a future where computation knows no bounds.",
"author": "ChatGPT",
"image": "https://www.example.com/images/sample.jpg",
"category": "Tech Insights",
"tags": [
"#QuantumComputing",
"#FutureTech" 
]

}
```

#### - Partial Update

Allows to update a blog post, specifying id as mandatory query param and all the other fields are optional
(title: String, content: String, author: String, image: String, category: String, tags: List<String>)

- Return `401` if uses is unknown
- Return `200` if post has been fully updated
- Return `400` if try to update full post but content is too long
- Return `500` if update goes in error

```
PUT http://localhost:8090/my-fancy-blog/v1/posts/{{id}}/update/?title=Journey into the Quantum Realm: Exploring the Future of ChatGPT
Content-Type: application/json
X-User: user
```

#### - Category Update

Allows to update the category of a blog post, specifying {{id}} and category

- Return `401` if uses is unknown
- Return `200` if category has been updated
- Return `500` if update goes in error

```
PUT http://localhost:8090/my-fancy-blog/v1/posts/{{id}}/update-category/?category=Cutting-Edge Technology
Content-Type: application/json
X-User: admin
```

## Decisions

- For sake of simplicity, the repository has implemented as an in memory map, with an interface, which allows to
  implement another repository to make the same operations with any kind of storage.
- The operation to assign a category to a post, it was understood as a category update of a blog post.
- All the operations can be made by `user`, DELETE operation is only allowed to `admin` (any other user is not allowed to execute any operation)
