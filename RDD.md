**RDD — Resilient	Distributed	Dataset**

**Resilient	Distributed	Dataset	(aka	RDD)**	is	the	primary	data	abstraction	in	Apache	Spark
and	the	core of Spark.

A	RDD	is	a	resilient	and	distributed	collection	of	records	spread	over	one	or	many	partitions.

One	could	compare	RDDs	to	collections	in	Scala,	i.e.	a	RDD	is	computed	on
many	JVMs	while	a	Scala	collection	lives	on	a	single	JVM.

Using	RDD	Spark	hides	data	partitioning	and	so	distribution	that	in	turn	allowed	them	to
design	parallel	computational	framework	with	a	higher-level	programming	interface	(API)	for
four	mainstream	programming	languages.

The	features	of	RDDs:

**Resilient** : fault-tolerant	with	the	help	of	RDD	lineage	graph	and	so	able	to
recompute	missing	or	damaged	partitions	due	to	node	failures.

**Distributed**: with	data	residing	on	multiple	nodes	in	a	cluster.

**Dataset**: is	a	collection	of	partitioned	data	with	primitive	values	or	values	of	values,	e.g.
tuples	or	other	objects	(that	represent	records	of	the	data	you	work	with).

Beside	the	above	traits	(that	are	directly	embedded	in	the	name	of	the	data	abstraction	-
RDD)	it	has	the	following	additional	traits:

**In-Memory**: 	i.e.	data	inside	RDD	is	stored	in	memory	as	much	(size)	and	long	(time)	as
possible.

**Immutable	or	Read-Only,**: i.e.	it	does	not	change	once	created	and	can	only	be
transformed	using	transformations	to	new	RDDs

**Lazy	evaluated,**	i.e.	the	data	inside	RDD	is	not	available	or	transformed	until	an	action
is	executed	that	triggers	the	execution.

**Cacheable,**	i.e.	you	can	hold	all	the	data	in	a	persistent	"storage"	like	memory	(default
and	the	most	preferred)	or	disk	(the	least	preferred	due	to	access	speed).

**Parallel,**	i.e.	process	data	in	parallel.

**Typed** — RDD	records	have	types,	e.g.	 	Long	 	in	 	RDD[Long]	 	or	 	(Int,	String)	 	in
	RDD[(Int,	String)]	
  
 **Partitioned** — records	are	partitioned	(split	into	logical	partitions)	and	distributed	across
nodes	in	a	cluster.

**Partitions**	are	the	units	of	parallelism.	You	can	control	the	number	of	partitions	of	a	RDD
using	repartition	or	coalesce	transformations.	Spark	tries	to	be	as	close	to	data	as	possible
without	wasting	time	to	send	data	across	network	by	means	of	RDD	shuffling,	and	creates
as	many	partitions	as	required	to	follow	the	storage	layout	and	thus	optimize	data	access.	It
leads	to	a	one-to-one	mapping	between	(physical)	data	in	distributed	data	storage,	e.g.
HDFS	or	Cassandra,	and	partitions.

RDDs	support	two	kinds	of	operations:
**transformations**	-	lazy	operations	that	return	another	RDD.
**actions**	-	operations	that	trigger	computation	and	return	values.

The motivation to create RDD were two types of applications that current computing frameworks handle inefficiently:
**iterative algorithms** in machine learning and graph computations.
**interactive data mining**	tools as ad-hoc	queries	on the same dataset.
The	goal	is	to	reuse	intermediate	in-memory	results	across	multiple	data-intensive
workloads	with	no	need	for	copying	large	amounts	of	data	over	the	network.
