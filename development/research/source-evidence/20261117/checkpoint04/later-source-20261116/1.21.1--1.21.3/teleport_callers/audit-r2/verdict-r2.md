# Independent narrow source audit — teleport caller inventory r2

## Verdict

**ACCEPT** the narrow correction to the bounded 1.21.1→1.21.3 ServerPlayer teleport/correction caller inventory. This accepts the added SpectateCommand camera route and its exact source binding only; the previously reviewed caller and overload inventory is reused unchanged from the bound r1 audit. It does not establish pair-wide source freeze, implementation applicability, or runtime parity.

## Immutable inputs

- Corrected report: `build/later-source-20261116/1.21.1--1.21.3/teleport_callers/teleport-caller-inventory-r2.md`
- Report SHA-256: `809EB150E498A49231F143A32A43413614F3B34B60D997307AF9C9AF1D4B6940`
- Prior REQUEST CHANGES verdict: `build/later-source-20261116/1.21.1--1.21.3/teleport_callers/audit/verdict.md`
- Prior verdict SHA-256: `AA5A76DE2BAF8F2F78E9CD486AFC9ABAECE5CA59AE413BFEA3D34B34C05D4430`

## Changed-claim checks

The report adds `net/minecraft/server/commands/SpectateCommand.java` to both endpoint source identity rows, with SHA-256 `b1166db677fcc3dd9542fdce6acf54b99c0636dd06332617e0e7cd53a239efb0`. I independently hashed the canonical file at both Mojmap endpoints; both match this value and the exact relative-path entries in `decompiled_minecraft/1.21.1/mojmap.sources.sha256` and `decompiled_minecraft/1.21.3/mojmap.sources.sha256`.

At line 59 in both files, `SpectateCommand.spectate` calls `ServerPlayer.setCamera(target)`. Before that call, it rejects self-targeting and requires the command-source player to be in spectator mode. The target is nullable, so the same route can clear the camera. This verifies the omitted direct camera caller and its eligibility boundary. The r2 report also preserves the previously reviewed `ServerPlayer.setCamera` behavior that routes a changed camera entity in a `ServerLevel` to the paired correction overloads.

The prior audit's unchanged checks of the endpoint identities, correction writers, other listed callers, and separate 1.21.2 boundary remain bound to the prior report/verdict; this follow-up did not reopen them.

## Limits

Acceptance covers only the corrected bounded caller inventory. Other event producers and dependencies listed as open in the report remain open. No pair freeze, product conclusion, or runtime parity is claimed.
