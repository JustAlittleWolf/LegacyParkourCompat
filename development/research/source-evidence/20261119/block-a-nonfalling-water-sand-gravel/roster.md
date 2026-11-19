# Frozen independent source roster

Assignment: exact source audit of LiquidBlock flow and the sand/gravel non-falling provider case across every stable release from 1.8 through 1.12.2 (24 versions).

Frozen before inspecting the author evidence. The source scope per version is LiquidBlock.java (getFlow and direct helpers), SandBlock.java, GravelBlock.java, Blocks.java, plus their caller/state inputs needed to establish flow and material classification. Mapping family is Ornithe Feather. The roster binds each source file to its exact SHA-256 and the accepted provenance artifact by frozen-roster.tsv.

Predetermined independent question: establish whether ordinary dry sand/red sand/gravel at a side cell independently contributes zero eastward liquid-flow weight while an independently enclosed north level-1 water neighbor of a level-0 center source contributes nonzero north flow; also determine whether legal 1.8 client-tick placement of a non-falling block item supports the optional placement case.

Source coverage is all versions listed in frozen-roster.tsv; no product sources, source history, research/backlog/wiki, or author scratch read before this roster freeze.


Dependency roster, frozen before author evidence: Block.java (historical numeric registry and IDs), FallingBlock.java (base material), SandBlock__Variant.java (ordinary/red metadata), Material.java (blocksMovement), World.java (liquid drag query and velocity contribution), and Entity.java (player-reachable ordinary tick). Their exact version hashes are in dependency-roster.tsv.
