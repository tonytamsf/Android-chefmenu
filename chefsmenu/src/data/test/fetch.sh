curl -X POST https://api.locu.com/v2/venue/search -d '{
  "api_key" : "f165c0e560d0700288c2f70cf6b26e0c2de0348f",
  "fields" : [ "name", "location", "contact","menus" ],
  "venue_queries" : [
    {
      "locu_id" : "5ea0bbf14816ecd9a155"
    }
  ]
}' | json_pp
