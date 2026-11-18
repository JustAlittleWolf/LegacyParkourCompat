# Eastward-facing Stone retraction union in 1.9–1.10.2

In the accepted sticky-piston layout, retracting the EAST-facing piston pulls plain Stone into a non-source destination cell. Exact 1.9 Ornithe Feather Gen2 build.2 `MovingBlock.java:130–134` and `MovingBlockEntity.java:75–88` use the current and previous progress shapes for ordinary collision collection. The independently checked 1.9.1–1.10.2 aliases retain that dispatch and the float `1.0F - progress` translation. Exact 1.11 `MovingBlock.java:134–140` and `MovingBlockEntity.java:295–316` switch standard collision enumeration to the current sample. Exact 1.8.9 `MovingBlock.java:118–130,162–193` likewise gives no clip for this held query, using the different single-shape provider.

At current progress 0.5F and previous progress 0.0F, the old union spans owner-local X `[0.5,2]` and the 26.2 current shape spans `[0.5,1.5]`. In the reviewed coordinates the owner is X=1, the player starts at minX 3.01, and the swept minX is 2.99. The ordinary query admits the old union and clips -0.02 to approximately -0.01. The initial box is outside the old union, so the strict direct-push entity query excludes it. This establishes the ordinary collision operation conditional on observing the phase and query together.

The 1.9 scanner includes the owner at its expanded X boundary (`World.java:893–928`). Where 26.2 supplies the scan, the old owner is still visited because the moving piston is dynamic (`Blocks.java:844–852` and `BlockBehaviour.java:577–578`). The existing shape hook can supply the union before native shape admission. Product admission requires the verified native current shape and a loaded Stone-floor/air envelope around the ordinary on-foot sweep. The source comparison makes no event-scheduling or runtime trajectory claim.

| Reviewed artifact | SHA-256 |
| --- | --- |
| Exact 1.9 endpoint author | df7c99bc2c5f67396184ace0a7d8204540ab8ea6dd30e36a3e45c376653535b5 |
| Earlier/later alias author | f4d5f2051e28ab3f52f60994f9ee215cad0ef1bb7eb826e7e670cae51bf24f2a |
| Independent alias and 1.11 cutover audit | e289ff9fdf209f4d60f9a0485e3711beb597ca644303ea7ba8be33f49405852b |
