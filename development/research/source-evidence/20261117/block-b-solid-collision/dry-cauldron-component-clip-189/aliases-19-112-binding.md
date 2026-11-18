# Dry cauldron component clipping through 1.12.2

The [original canopy witness](binding.md) and [1.8 alias check](aliases-binding.md) extend to exact 1.9 through 1.12.2 for the same loaded dry-cauldron fixture, authorized placement and immediate ordinary local move. Every checked cauldron offers floor, west, north, east and south in that order. The signed Z sweep strictly intersects only east and south, in that order. East already overlaps the player's Z range. South caps +Z to 0.07499997615814202 for the corrected 0.6F-width box. Both canopy-limited step alternatives preserve the same horizontal gain, so the tie restores the ordinary clipped box.

From 1.11.1 the ordinary World collector passes forceShape=false. The cauldron hook ignores that flag and still supplies the same component order. The base slab only touches the swept query at feet height. The canopy enters the upward-expanded sweep and prevents a clear step over the wall. The same authorized immediate-move condition, strict horizontal overlap and absence of additional providers apply throughout. Natural reachability and runtime parity remain open.

The dry keyed movement collector resolves at V1_12. Closest-later selection now covers all verified stable 1.8 through 1.12.2 groups. V1_13 and later resolve without it. The selected provider still uses the same strict clearance gate and the same authored part list as the existing occupied basin union. Other cauldron keys, component keys, step resolvers and ordinary shape paths retain their own behavior.

Eight consumed source files matched each checked exact Feather source map. The 1.9.2 hidden provenance SHA-256 below corrects a missing character in the raw reports. The source identities and author/review receipts are retained below.

| Release | Source identity | SHA-256 |
|---|---|---|
| 1.9 | Mapping-root hidden provenance | 8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a |
| 1.9.1 | Mapping-root hidden provenance | e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a |
| 1.9.2 | Mapping-root hidden provenance | 9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510 |
| 1.9.3 | Mapping-root hidden provenance | 43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a |
| 1.9.4 | Release-root source manifest | c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19 |
| 1.10 | Mapping-root hidden provenance | df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da |
| 1.10.1 | Mapping-root hidden provenance | a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f |
| 1.10.2 | Release-root source manifest | 91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71 |
| 1.11 | Release-root source manifest | 8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca |
| 1.11.1 | Release-root source manifest | 0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d |
| 1.11.2 | Release-root source manifest | d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0 |
| 1.12 | Mapping-root hidden provenance | 27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467 |
| 1.12.1 | Mapping-root hidden provenance | 0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8 |
| 1.12.2 | Release-root source manifest | b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da |

- [aliases-19-author.md](aliases-19-author.md), SHA-256 2749ce7ec7b810f27353bf5ac951dc41a7c81d2a51c461f87bc54576dafaccf0.
- [aliases-110-author.md](aliases-110-author.md), SHA-256 ca899883a92e7e0bfad559fe17b36b81748ca4a66659b8be75ed18fcadcd2417.
- [aliases-19-110-verdict.md](aliases-19-110-verdict.md), SHA-256 3abb3f582f9fee3478d25149dabe274bbdbe94a5b1de71374fdfddf400934997.
- [aliases-111-112-author.md](aliases-111-112-author.md), SHA-256 7f3a4a81caca8f200a65396237e3a2cb4aeac6d3649413065f71080c3d67694d.
- [aliases-111-112-verdict.md](aliases-111-112-verdict.md), SHA-256 b94209abd1ba4d798f154a4fb6015fa0e5c75a9a197739ce35967afdf3a294d1.
