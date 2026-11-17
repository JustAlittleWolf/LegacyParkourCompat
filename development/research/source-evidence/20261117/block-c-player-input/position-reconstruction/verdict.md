# Independent source verdict: post-collision player position reconstruction

## Conclusion

**ACCEPT the core source difference for the sampled release snapshots, with one consequence correction.** The 1.13, 1.13.1, 1.13.2, 1.14 Yarn, 1.14.4, 1.15.2, and 1.16.5 sources reconstruct coordinates from the resulting box. The 1.17 and 1.17.1 sources instead add the clipped vector to coordinates and rebuild the box through `setPos`. These exact snapshots support an unknown patch boundary within 1.16.5–1.17; they do not establish a cutover at an intermediate release or coverage of every intervening release.

## Evidence review

I independently checked the `Entity.move`/helper or `setPos` slices and the local-player wrapper relevant to the claim. The nine `Entity.java` hashes in the draft match their exact local source manifests or nested provenance source entries. The hashes and identities stated in the draft are accurate. As the draft itself records, the manifest/provenance schemas do not independently establish every upstream artifact chain; that limitation does not undermine the directly inspectable source bodies.

The arithmetic witness is reachable in ordinary physics: a default-width player (`0.6F`), positioned at `x = 0.1` in empty air, can move horizontally by `0.3` with zero Y/Z movement while airborne. The displacement clears the `1.0E-7` gate in 1.14–1.17; in 1.13 the ordinary move reaches the final reconstruction after collision/step handling. The old translated-box midpoint is `0x3FD9999999999999`; the newer coordinate sum is `0x3FD999999999999A`. Rebuilding the box from the newer coordinate changes both X faces by one double ULP in this witness.

The 1.13 axis and box operations do **not** invalidate this narrow comparison: its Y, X, then Z collision translations and possible step-box selection precede the final `setPositionFromShape` call. They are distinct earlier operations and mean a broader claim about identical `move` behavior would be too broad. Applicability also differs: 1.13 calls the reconstruction after its ordinary movement path even when the requested vector is zero, whereas 1.14–1.16.5 reconstruct only when the clipped vector passes the squared-length gate. The draft describes this difference and the witness stays within the shared nonzero path.

## Required correction to the draft

The supplied witness does not show a changed float input to the 1.13 local player's auto-jump response. That wrapper casts `(this.x - previousX)` to `float`; for both witness outcomes, the cast is `0x3E99999A` (`0.3F`). The final double position and AABB differ, but the stated witness does not prove the rounding is visible through that particular float response. Qualify or remove the sentence claiming visibility to the downstream response. Other code reading the resulting double position or box can still observe the distinction.

## Binding and terminal status

Bound draft: `position-reconstruction-source/comparison.md`  
Draft SHA-256: `26d9d3ba0644778d27dcc66652e59bfc267c018125fff933c41a57751f32e82a`  
Verdict: `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\position-reconstruction-audit\verdict.md`  
Terminal status: **COMPLETED**