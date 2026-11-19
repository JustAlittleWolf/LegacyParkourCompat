# Independent early respawn span audit

## Verdict

**Accepted for the bounded 1.8–1.14.4 span:** every one of the 32 stable release identities in the two bound evidence files replaces the client player with a fresh object and reaches a reset path that zeroes the replacement's velocity when its world/level is present. The client respawn path does not transfer old-player velocity. Respawn packet state does not contain a keep-data flag or equivalent. This confirms the predecessor behavior throughout the assigned span; it says nothing about the introduction point after 1.14.4.

The exact path is `ClientPlayNetworkHandler.handlePlayerRespawn` → `Minecraft.respawnPlayer` → fresh local player → reset for 1.8–1.13.2. Starting in 1.14, the handler creates the replacement inline. In 1.14.1–1.14.3 it calls `PlayerEntity.afterSpawn`, which sets pose then calls `Entity.afterSpawn`; the base method zeros velocity. In 1.14 and 1.14.4 it calls `Entity.resetPos`, which zeros velocity. Each reset is gated on non-null world/level, satisfied on the ordinary reachable respawn construction path.

## Bound inputs and source identity audit

The assigned evidence files were verified at the exact requested SHA-256 values:

- `evidence.md`: `85cedc7ed307a940af4513e6c3daf9865ab22e4797bd251a70f34c416ef0959d`
- `evidence-supplement.md`: `eb37a90161381e6a748ca2507a4af1815d9d4c5fe2ec2bff083fea84e9b582ee`

All 32 requested identities have mapped local source for the respawn handler, replacement/reset owner and packet. The 12 outer `*.ready.json` source-manifest digests match `evidence.md` exactly: 1.8.9, 1.9.4, 1.10.2, 1.11, 1.11.1, 1.11.2, 1.12.2, 1.13, 1.13.1, 1.13.2, 1.14, and 1.14.4. For the other 20 aliases, the source-file SHA-256 values were checked against the nested `.decompile-provenance.json` `sources` map for each relevant handler, helper/reset owner, and packet; 1.14.1–1.14.3 also checked `PlayerEntity.java`. Every checked file matched its pin.

**Citation correction found:** the supplement attributes the 1.8 and 1.8.1 respawn handler to `ClientPlayNetworkHandler__10912577__95801570__70204262.java` and gives that alias's SHA (`d727fb…`). That generated alias has no `handlePlayerRespawn` method. The actual handler is in `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java`, line 801. Its source SHA matches the nested provenance pin (`1.8`: `1cca0ded9f1112d407b149931c1f067a104d2beed74f1e7070200e8c035a102d`; `1.8.1`: `65fe3f74154996a5b94a9d5534a4805b40e13be245d50178b838ce0e79802118`). Extracting that actual method gives the report's `7d0f30d4…` handler-body digest in both. This is a source-path/hash citation error; the behavior remains source-supported by the correctly pinned handler.

## Independent body groups and movement check

Exact method-body identity groups independently reproduced (hashes over the signature through closing brace, LF line breaks, excluding annotations):

- Handler: 1.8–1.8.1 `7d0f30d4…`; 1.8.2–1.11.2 `e5fb26c9…`; 1.12.2 and 1.13 `4f91e22e…`; 1.13.1–1.13.2 `0ecaaf09…`; 1.14 and 1.14.4 `c15a7cbb…`; 1.14.1 `afbc066c…`; 1.14.2–1.14.3 `d3dca98e…`.
- Replacement helper: 1.8–1.8.1 `018335eb…`; 1.8.2–1.11.2 `f391ce4b…`; 1.12–1.12.2 `f7530bf8…`; 1.13 `17c71540…`; 1.13.1–1.13.2 `e717a80c…`.
- Reset: 1.8–1.9.4 `3cedea6b…`; 1.10–1.12.2 `f420958d…`; 1.13–1.13.2 `9284c143…`; 1.14–1.14.4 Feather `4f05cfcd…`.
- 1.14.1–1.14.3 Yarn `Entity.afterSpawn` body is `aa8ac33b…`; the `PlayerEntity.afterSpawn` override is `142f0930…` for 1.14.1–1.14.2 and `51428124…` for 1.14.3.

The old helper bodies construct and assign a new local player, copy selected stats/recipe/synced data, and call the reset method; none copies the old velocity fields. Tracked/synced data copy is distinct from the entity's velocity state. The reset bodies set x/y/z velocity to `0.0`, or set the velocity vector to zero, under the world/level gate. In the inline 1.14.1–1.14.3 variant, `PlayerEntity.afterSpawn` delegates to the zeroing base method after setting pose. These exact replacements plus resets establish actual replaced-player state; the conclusion does not rest only on the absence of a packet flag.

I inspected the packet fields and read/write bodies across all 32 identities. Their fields/serialized data are dimension and respawn-world settings (and older difficulty/game mode/generator data as applicable); no velocity or keep-data bit is present. Handler body groups have only dimension/world replacement conditions and unconditionally proceed into the replacement path.

## Scope boundary

The audit accepts only the specified predecessor span and this respawn/old-player velocity delta. Canonical ready evidence starts at 1.8.9; the omitted patch aliases are source-supported by their nested provenance pins, but those aliases are not upgraded to outer-ready status. No exact later cutover or keep-data introduction is inferred. No product files, research index, generation, tests, builds, or runtime launches were changed or performed.
