# MyFancyBlog

### Operations

## - Create
  Allows to create a new blog post, for both `admin` and `user` users, as from the requests. Response codes are:
- `201` when the post has been created succesfully
- `400` when the post length exceeds the limit (1024 chars)
- `401` when the user is not recognized 
- `500` if an error occurs
```
POST http://localhost:8090/my-fancy-blog/v1/create/
Content-Type: application/json
X-User: user

{
"title": "String",
"content": "One morning, when Gregor Samsa woke from troubled dreams, he found himself transformed in his bed into a horrible vermin. He lay on his armour-like back, and if he lifted his head a little he could see his brown belly, slightly domed and divided by arches into stiff sections. The bedding was hardly able to cover it and seemed ready to slide off any moment. His many legs, pitifully thin compared with the size of the rest of him, waved about helplessly as he looked. "What's happened to me?" he thought. It wasn't a dream. His room, a proper human room although a little too small, lay peacefully between its four familiar walls. A collection of textile samples lay spread out on the table - Samsa was a travelling salesman - and above it there hung a picture that he had recently cut out of an illustrated magazine and housed in a nice, gilded frame. It showed a lady fitted out with a fur hat and fur boa who sat upright, raising a heavy fur muff that covered the whole of her lower arm towards the viewer. Gregor then tu",
"author": "String",
"image": "String",
"category": "String",
"tags": [
"somestring1",
"somestring2"

}
```


