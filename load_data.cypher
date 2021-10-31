MATCH (n)
DETACH DELETE n;

DROP INDEX city_name_index IF EXISTS;

LOAD CSV WITH HEADERS FROM "file:///data/connection.csv" as row
WITH row WHERE row.city1 IS NOT NULL
MERGE (c:City {name: row.city1});

CREATE INDEX city_name_index IF NOT EXISTS FOR (city: City) ON (city.name);

LOAD CSV WITH HEADERS FROM "file:///data/connection.csv" as row
WITH row WHERE row.city2 IS NOT NULL
MERGE (c:City {name: row.city2});

LOAD CSV WITH HEADERS FROM "file:///data/connection.csv" as row
MATCH (c1:City {name: row.city1})
MATCH (c2:City {name: row.city2})
MERGE (c1)-[:IS_CONNECTED {distance:toInteger(row.distance), duration:toFloat(row.duration), line_id:toInteger(row.line_id)}]-(c2);

LOAD CSV WITH HEADERS FROM "file:///data/driver_cost.csv" as row
MATCH (c1: City)-[conn:IS_CONNECTED {line_id:toInteger(row.line_id)}]-(c2: City)
WITH conn, row
SET conn.driver_cost_per_hr = toFloat(row.driver_cost_per_hr);

LOAD CSV WITH HEADERS FROM "file:///data/bus_cost.csv" as row
MATCH (c1: City)-[conn:IS_CONNECTED {line_id:toInteger(row.line_id)}]-(c2: City)
WITH conn, row
SET conn.bus_cost_per_km = toFloat(row.bus_cost_per_km);
