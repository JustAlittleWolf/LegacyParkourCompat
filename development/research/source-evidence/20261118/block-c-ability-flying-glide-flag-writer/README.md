# Ability-flight / fall-gliding flag-writer evidence (2026-11-18)

## Verified checkpoint

This checkpoint preserves twelve exact source report/verdict pairs for the ordinary local ability-flight / fall-gliding overlap and direct local flag-writer operation. Scope is the stated overlap when the synchronized fall-flying flag is already true and local `abilities.flying` becomes true, through the specific movement call and direct flag write. It does not establish network latency, authority synchronization guarantees, broad middle-range aliases, activation parity, runtime parity, or whole-pair coverage.

| Pair | Bounded disposition | Report SHA-256 | Verdict SHA-256 |
|---|---|---|---|
| 1.8.9→1.9 | 1.8.9 lacks Elytra; in 1.9 the ordinary creative-flight input can overlap with a live synced flag 7. After actual `Entity.move`, the local player clears flag 7. This is a source-reachable ordered path, not a latency guarantee; the adjacent fall-distance reset is separate. | `7e254aa6b797c34109e25610c87e5f7b9f4077e1c7e0fc77963bcf44ac12b8d0` | `50c4787f8075a388d47a84c666297c969014a4c2c6ef774ec317fbde6f5308d7` |
| 1.9→1.9.1 | Bounded no-difference: when local `canFly` and synced flag 7 are already true, ability-flight calls the glide branch through `Entity.move`, then the player wrapper clears its local flag. The clear is not server-authoritative. | `e178f379ac7f92bd8365ba554988636d2464c5e2c9b57148864be6e74387ed45` | `7268c67e0440efb3b04b3e04cd38554cc3971b4279c4ad9aae9bd24ddd2d1b36` |
| 1.9.1→1.9.2 | Accepted bounded overlap/order claim for the already-active local flag and ability-flight path. | `82a05bbf7722153fb176e127b17cd73639643670f24143a5afea2a6e4d489e08` | `ee88cbffd8b234b580fbfdb3e25a986a795fa54284af8b1e55903690b6df0193` |
| 1.9.2→1.9.3 | Accepted bounded overlap claim; exact branch ordering and direct flag clear are retained in the verdict. | `9fe71a3576cba85908946f975ad5b5b0c8e6a5cb4f81b1698ea3d88ee7a5bdc0` | `d9b9583d9b6621631d855b26e83c72d07d9988da45d60cbe399a7408b6fcba63` |
| 1.9.3→1.9.4 | Accepted bounded claim for the direct ability-flight / live fall-flying-flag overlap. | `23f3b8cd6811d735e42eb42815648f1e23ca66f2bc1b7bc1773e5d8b4fba34d4` | `94b414a20c2073270e58cb069b2cc13327de3879ab0db4c99678460c88996de2` |
| 1.9.4→1.10 | Accepted bounded no-difference; audit corrects hidden 1.10 provenance as recorded in its exact verdict. | `db5fa58f3e1f9aee2668777243e821222a6c5d9fece3efef39da711d2696bbb9` | `7a81fe79f54cb62bbf77717e556d36797fd8c2c9fe7c2af433fd55392c201edb` |
| 1.10→1.10.1 | Accepted bounded source comparison for the specified local flag-writer operation. | `a97cbb762ae4796358c82e58d3c97665d229da6c8e5788b76613650263371856` | `21ed3e447789502fac7cbbe669ea024c1b621c2322a801f7b8a8156a1acbbdad` |
| 1.10.1→1.10.2 | Bounded no-difference. The audited enclosing member is `LivingEntity.tick()`; `travel` is a profiler label, not a separate enclosing method. | `b524af9131bbf500e3152b17e9a5a0724d14db8b8288862e3a3095aa79b7117d` | `a060ea8090a4834cfee0ebb541eca6473d2ce5ed988e3a3badfa7dcfa2ab33b6` |
| 1.10.2→1.11 | Same scoped direct clear; the changed `MoverType` call is a distinct operation and not collapsed into this flag-writer claim. | `c0e119289a043e5745c678dd51fb3b60a1bdec1ae8a80473977f452ae405d274` | `e638284efa60b8c6617ab47768487a191b571611ec64c9b85d6ef5e0119f12e1` |
| 1.21→1.21.1 | Accepted direct local post-travel flag-7 clear. The separate `fallDistance` reset remains a different operation. | `a4213385fe5541d539aa53051582a1577ba595e934a31e367f4c0bb2397b90ae` | `0384217d05a4e44ca91a8de8f3a0c94c5226e98291ec5838df228638b7e5663a` |
| 1.21.1→1.21.2 | The old direct local clear is removed in the audited scope. Verdict records the hidden in-tree provenance correction for 1.21.2; no broader source-tree or runtime claim is made. | `354ab88749e8c7710741372e34c0c9a5f98a8827de22401c9612d41a2763e040` | `c3ea40858250beeeba71d7ba7535809b16a33308b46a6c4cbd8cb09b2ac67ccd` |
| 1.21.1→26.2 | Accepted ordinary synchronized overlap: 1.21.1 clears flag 7 after `super.travel`, while 26.2 does not in that post-travel branch. No duration or later-authority guarantee is inferred. | `71ef39910cc182c1baf12e691bfa4d35be866f27081f6b191cf0964e7c01e68c` | `f2a438785473cfee30687d7e88b704293887c20b7814e7fd2cb9fcf4c5023c2d` |

The ability-flight `fallDistance` reset is treated as a separate operation; the flag-writer comparison does not expand its timing claim. No server movement, packet latency, or general .9→1.12.2 / .20.6→.21 alias result is inferred.

## Pending coverage

Additional patch-boundary pairs within 1.9.4→1.12.2 and 26.0.6→1.21 are still in progress. This is a verified subset checkpoint, not the final archive; wait for the remaining exact scope list before final bundle freeze.

Every included report and verdict was rehashed, checked for exact input binding, and copied byte-for-byte. No implementation, product parity, or runtime claim is made.