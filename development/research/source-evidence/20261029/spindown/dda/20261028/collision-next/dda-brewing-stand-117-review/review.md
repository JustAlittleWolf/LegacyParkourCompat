# Independent review: 1.8.9 Brewing Stand provider and cache closure

Verdict: **ACCEPT within the stated provider/state and cache-read scope**.

## Bound input identity

Reviewed `.native-source/20261028/collision-next/dda-brewing-stand-117-closure/report.md` (SHA-256 `A1EBD7C0882ED4244D5081255BC3DF2D4DE982691F4FCB13E37A8CE48103202F`) and `source-hashes.sha256` (SHA-256 `B2B2152BF7A6B875CA8B4EB18C312FB0C6613183CADF438BFF64404192A359C6`). Independently recomputed all 20 entries from `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`; all matched. All 12 cited Java-file entries also matched their exact `1.8.9/ornithe-feather.sources.sha256` or `26.2/unobfuscated.sources.sha256` rows.

## Claim review

- **Old identity/state:** `Block.java:1075` registers numeric ID 117 as `brewing_stand`. The `BrewingStandBlock` default state has three false Boolean properties named `has_bottle_0..2`; metadata reads/writes only those low three bits (`BrewingStandBlock.java:29-35,145-170`). Therefore 0–7 represent the eight combinations and 8–15 decode to the same combinations as 0–7. The properties do not affect the collision method.
- **Mutable collision writes:** `Block` initializes instance bounds to the unit cube (`Block.java:201`). `BrewingStandBlock.addCollisions` sets the narrow centered stem, delegates to the superclass collision insertion, calls `resetShape`, then delegates again; its reset bounds are the full-footprint 2/16-high platform (`BrewingStandBlock.java:64-73`). The base provider builds a world-space box from the current mutable bounds and adds it on query intersection (`Block.java:339-348`). This confirms both write order and final cached platform bounds after the method completes.
- **Ray-trace read:** `Block.rayTrace` calls virtual `updateShape` before reading cached bounds (`Block.java:456-466`); base `updateShape` is empty (`Block.java:596`) and BrewingStandBlock has no override. Thus this provider's ray-trace path observes current shared fields without refreshing them. The cited Entity/World caller references establish a route into this reader. This is a source-level observer dependency only; no camera-tick reachability or DDA visitation outcome is established.
- **Current alias/shape:** 26.2 registers `Blocks.BREWING_STAND` under `BlockItemIds.BREWING_STAND`, key `brewing_stand` (`Blocks.java:2567-2571`; `BlockItemIds.java:679`). The same three Boolean properties/default values exist. The current shape is state-independent: centered 2/16 stem from 2/16 to 14/16 and centered 14/16-wide platform from 0 to 2/16 (`BrewingStandBlock.java:35-47,61-62`; `Block.java:160-184`). Its platform spans 1/16 to 15/16 in X/Z, so it omits the old collision platform's outer 1/16 rim. The old stem below 2/16 adds no union outside the old platform footprint. The stated provider mismatch is correct.
- **Scope:** The report correctly separates immutable modern collision shape from the old mutable instance fields read by rayTrace and does not claim full ray traversal, camera reachability, chunk residency, or runtime parity.

No material source or scope issue found.

## Review limits

This accepts the bounded ID/state/provider and mutable-cache reader facts only. It does not accept a DDA adapter, an old last-writer equivalence claim, or a concrete movement/camera witness. Any adapter using the current Brewing Stand provider as the old collision provider would be incorrect for the omitted platform rim; any cache-emulation claim still depends on source-closed visitation and preceding writer history.
