Data Nuclues OutputFormatter is an OutputFormatter which store objects into databases. When there is a DBOutputFormatter, why do we need this? Well, in most of the applications, users write their own custom writable objects to store their MR output.

When we run another job to persist the output of a previous MR job into DB, using DBOutputFormatter, we need extract the values of the object and needs to set these values into prepared statement object.Moreover, current DBOutputFormatter supports only SQL Databases. If we want to store the results into any kind of database, we need to write our own OutputFormatter.

But if we have an OutputFormatterr, which supports all of the above operations, it will be great, right?
This is what DNOutputFormatter does.

DNOutputFormatter takes an object and persists that object into the DB. The object to be persisted using [Data Nucluas](http://www.datanucleus.org/products/datanucleus/index.html)  must be qualified as [Persistent Capable](http://www.datanucleus.org/products/datanucleus/jdo/class_mapping.html). It is required to put the  configured [Data Nucluas](http://www.datanucleus.org/products/datanucleus/index.html) database drivers and necessary [Data Nucluas](http://www.datanucleus.org/products/datanucleus/index.html) jars into job's classpath. Also along with these drivers, an optional file which maps the classes and its fields to tables and its columns (ORM) can be given.
