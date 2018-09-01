# root
GET http://localhost:8080/
```json
{
    "_schema": {
        "links": [
            {
                "href": "http://localhost:8080/users",
                "schema": {
                    "type": "object",
                    "properties": {
                        "limit": {
                            "type": "integer"
                        },
                        "offset": {
                            "type": "integer"
                        }
                    }
                },
                "targetSchema": {
                    "type": "object",
                    "properties": {
                        "limit": {
                            "type": "integer"
                        },
                        "members": {
                            "type": "array",
                            "items": {
                                "type": "object",
                                "properties": {
                                    "birth": {
                                        "type": "string",
                                        "format": "date-time"
                                    },
                                    "name": {
                                        "type": "string"
                                    },
                                    "value": {
                                        "type": "string",
                                        "format": "uuid",
                                        "pattern": "^[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89ab][a-f0-9]{3}-[a-f0-9]{12}$"
                                    }
                                }
                            }
                        },
                        "offset": {
                            "type": "integer"
                        },
                        "total": {
                            "type": "integer"
                        }
                    }
                },
                "method": "GET",
                "rel": "get-users",
                "relType": "other",
                "mediaType": "application/json",
                "target": "_blank"
            },
            {
                "href": "http://localhost:8080/users/{id}",
                "schema": {
                    "type": "object",
                    "properties": {
                        "id": {
                            "type": "string"
                        }
                    }
                },
                "targetSchema": {
                    "type": "object",
                    "properties": {
                        "birth": {
                            "type": "string",
                            "format": "date-time"
                        },
                        "name": {
                            "type": "string"
                        },
                        "value": {
                            "type": "string",
                            "format": "uuid",
                            "pattern": "^[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89ab][a-f0-9]{3}-[a-f0-9]{12}$"
                        }
                    }
                },
                "method": "GET",
                "rel": "get-user-instance",
                "relType": "other",
                "mediaType": "application/json",
                "target": "_blank"
            }
        ]
    }
}
```

# user (after adding one user)
GET http://localhost:8080/users
```json
{
    "members": [
        {
            "value": "ba2fb9f3-6bab-4202-bb7b-f73872ae3629",
            "name": "John Doe",
            "_schema": {
                "links": [
                    {
                        "href": "http://localhost:8080/users/ba2fb9f3-6bab-4202-bb7b-f73872ae3629/summary",
                        "targetSchema": {
                            "type": "object",
                            "properties": {
                                "birth": {
                                    "type": "string",
                                    "format": "date-time"
                                },
                                "name": {
                                    "type": "string"
                                },
                                "value": {
                                    "type": "string",
                                    "format": "uuid",
                                    "pattern": "^[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89ab][a-f0-9]{3}-[a-f0-9]{12}$"
                                }
                            }
                        },
                        "rel": "self",
                        "mediaType": "application/json",
                        "method": "GET"
                    },
                    {
                        "href": "http://localhost:8080/users/ba2fb9f3-6bab-4202-bb7b-f73872ae3629",
                        "targetSchema": {
                            "type": "object",
                            "properties": {
                                "birth": {
                                    "type": "string",
                                    "format": "date-time"
                                },
                                "name": {
                                    "type": "string"
                                },
                                "value": {
                                    "type": "string",
                                    "format": "uuid",
                                    "pattern": "^[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89ab][a-f0-9]{3}-[a-f0-9]{12}$"
                                }
                            }
                        },
                        "rel": "canonical",
                        "mediaType": "application/json",
                        "method": "GET"
                    }
                ]
            }
        }
    ],
    "total": 1,
    "offset": 0,
    "limit": 20,
    "_schema": {
        "links": [
            {
                "href": "http://localhost:8080/users/{id}",
                "schema": {
                    "type": "object",
                    "properties": {
                        "id": {
                            "type": "string"
                        }
                    }
                },
                "targetSchema": {
                    "type": "object",
                    "properties": {
                        "birth": {
                            "type": "string",
                            "format": "date-time"
                        },
                        "name": {
                            "type": "string"
                        },
                        "value": {
                            "type": "string",
                            "format": "uuid",
                            "pattern": "^[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89ab][a-f0-9]{3}-[a-f0-9]{12}$"
                        }
                    }
                },
                "method": "GET",
                "rel": "instance",
                "relType": "other",
                "mediaType": "application/json",
                "target": "_blank"
            },
            {
                "href": "http://localhost:8080/users?offset=0&limit=20",
                "schema": {
                    "type": "object",
                    "properties": {
                        "limit": {
                            "type": "integer"
                        },
                        "offset": {
                            "type": "integer"
                        }
                    }
                },
                "targetSchema": {
                    "type": "object",
                    "properties": {
                        "limit": {
                            "type": "integer"
                        },
                        "members": {
                            "type": "array",
                            "items": {
                                "type": "object",
                                "properties": {
                                    "birth": {
                                        "type": "string",
                                        "format": "date-time"
                                    },
                                    "name": {
                                        "type": "string"
                                    },
                                    "value": {
                                        "type": "string",
                                        "format": "uuid",
                                        "pattern": "^[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89ab][a-f0-9]{3}-[a-f0-9]{12}$"
                                    }
                                }
                            }
                        },
                        "offset": {
                            "type": "integer"
                        },
                        "total": {
                            "type": "integer"
                        }
                    }
                },
                "rel": "self",
                "mediaType": "application/json",
                "method": "GET"
            },
            {
                "href": "http://localhost:8080/users/{id}",
                "schema": {
                    "type": "object",
                    "properties": {
                        "birth": {
                            "type": "string",
                            "format": "date-time"
                        },
                        "id": {
                            "type": "string",
                            "format": "uuid",
                            "pattern": "^[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89ab][a-f0-9]{3}-[a-f0-9]{12}$"
                        },
                        "name": {
                            "type": "string"
                        }
                    },
                    "required": [
                        "id",
                        "name"
                    ]
                },
                "rel": "create",
                "mediaType": "application/json",
                "method": "POST"
            }
        ]
    }
}
```

