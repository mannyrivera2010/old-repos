package models

import (
    "encoding/json"
    "io"
)

type Movie struct {
    Title  string `json:"title"`
    Rating string `json:"rating"`
    Year   string `json:"year"`
}

func (movie *Movie) ToJson() string {
    b, _ := json.Marshal(movie)
    return string(b)
}

func MovieFromJson(data io.Reader) *Movie {
    var o *Movie
    json.NewDecoder(data).Decode(&o)
    return o
}
