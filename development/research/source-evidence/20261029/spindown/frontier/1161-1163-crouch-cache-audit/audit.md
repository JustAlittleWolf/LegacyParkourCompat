# Independent crouch-cache source audit: 1.16.1–1.16.3

## Audit identity

Reviewed author closure: `.native-source/20261028/frontier-c/1161-1163-crouch-cache/closure.md`, SHA-256 `1df0648a16f909962dca918a0faa9036c0582159de155a5e812692ab9d59e762`.

Canonical source trees inspected read-only at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.16.1,1.16.2,1.16.3}\mojmap`. The assigned accepted dependency was `.native-source/20261028/frontier-c/1152-1161-input-provider-closure/closure.md`.

## Source identity audit

- For 1.16.1, the parent ready marker declares exact version `1.16.1`, Mojmap, 3,572 Java files, and manifest SHA-256 `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`. Recomputed manifest hash matches.
- For 1.16.2, the parent ready marker declares exact version `1.16.2`, Mojmap, 3,516 Java files, and manifest SHA-256 `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`. Recomputed manifest hash matches.
- For 1.16.3, the parent ready/provenance/source-manifest files are absent. The mapping-root `.decompile-provenance.json` is present; its SHA-256 is `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498`. Its embedded `sources` map covers 3,516 source paths. I independently recomputed the hashes of every source used in this audit and matched them to that map. The embedded inputs identify 1.16.3 version metadata, Mojang client JAR, and Mojmap mapping artifact with the hashes recorded in the author closure. This limits the provenance claim to the mapping-root manifest and checked files; it does not make the missing parent marker present.
- For 1.16.1 and 1.16.2, recomputed hashes for `LocalPlayer.java`, `Input.java`, `KeyboardInput.java`, `ClientPacketListener.java`, `MultiPlayerGameMode.java`, `Entity.java`, and `Player.java` match their parent source manifests. In 1.16.3, those file hashes match the embedded source map. Provider and relevant support files `Options.java`, `KeyMapping.java`, `ToggleKeyMapping.java`, and `LivingEntity.java` were also checked against their maps. `LocalPlayer.java` differs between 1.16.1 and 1.16.2, while 1.16.2 and 1.16.3 `LocalPlayer.java` hashes match exactly; the 1.16.1 whole-file difference does not by itself establish a difference in this operation.

## Independent source findings

1. `Input.java` and `KeyboardInput.java` are byte-identical in all three versions. The normal login route installs `new KeyboardInput(options)` on the local player, and the respawn route installs it on the replacement. `KeyboardInput.tick(boolean)` reads the four directional mappings, forms signed unit/zero float impulses, reads jump and shift, and only then scales the two directional impulses by `0.3` when its argument is true. This agrees with the accepted 1.15.2→1.16.1 provider-closure evidence and extends the checked provider-side identity through 1.16.3.

2. The three `LocalPlayer.aiStep()` excerpts have the same relevant order and expression: after the portal handler, snapshot the previously stored `input.jumping` and `input.shiftKeyDown`, compute the sprint impulse gate, assign cached `crouching`, then call `input.tick(this.isMovingSlowly())`. The crouch assignment retains the same gates: not flying, not swimming, crouch pose admitted, and either stored shift or not sleeping while standing pose is unavailable. `isMovingSlowly()` reads cached crouching or the visual-crawl fallback. Thus it consumes the existing crouch cache before this tick's keyboard poll updates `shiftKeyDown`; the keyboard provider then applies `0.3` only to directional impulses. The checked `Entity` and `Player` predicates retain the crouch-pose collision and swimming gates, while `isVisuallyCrawling()` remains visual swimming outside water.

3. Sprint timing and state are distinct from the crouch cache and input shift field. Each version increments `sprintTime` and decrements a positive trigger timer before the pre-poll snapshots. `wasSprinting` and `wasShiftKeyDown` are packet-transition latches initialized by the constructor and updated by the position-send path. The sprint impulse threshold remains `input.hasForwardImpulse()` underwater and `input.forwardImpulse >= 0.8` otherwise. Checked local-player server-AI transfer still copies input side impulse, forward impulse, and jump to movement fields under `isControlledCamera()`.

4. Respawn constructs a replacement with the prior player's shift and sprint values, which the constructor stores in the two packet-transition latches. `crouching` has no constructor assignment (only its field declaration and the `aiStep` assignment); the replacement's `input` field is assigned a newly constructed keyboard provider after construction. Consequently the prior crouch cache and `Input` object are not copied by this route; the new Java fields begin at defaults before polling.

## Audit verdict and limits

**Independent audit accepts the bounded no-difference claim** for the crouch-cache/slow-input operation across inspected 1.16.1, 1.16.2, and 1.16.3 sources, including its checked provider dependency, ordering, sprint latches and replacement path. The source differences between 1.16.1 and later whole files do not change the compared operation slices. No new difference was found in the assigned scope.

This is not full player-tick inventory closure, does not establish behavior for uninspected intermediate versions, and makes no product/runtime-parity claim. The 1.16.3 parent-marker absence remains a specific provenance limitation; its mapping-root source map and checked file hashes support the inspected evidence.
