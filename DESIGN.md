---
name: Obsidian Telemetry
colors:
  surface: '#101418'
  surface-dim: '#101418'
  surface-bright: '#353a3e'
  surface-container-lowest: '#0a0f13'
  surface-container-low: '#181c20'
  surface-container: '#1c2024'
  surface-container-high: '#262a2f'
  surface-container-highest: '#31353a'
  on-surface: '#e0e3e8'
  on-surface-variant: '#b9cacb'
  inverse-surface: '#e0e3e8'
  inverse-on-surface: '#2d3135'
  outline: '#849495'
  outline-variant: '#3a494b'
  surface-tint: '#00dce6'
  primary: '#e0fdff'
  on-primary: '#00373a'
  primary-container: '#00f2fe'
  on-primary-container: '#006a70'
  inverse-primary: '#00696f'
  secondary: '#e0b6ff'
  on-secondary: '#4c007d'
  secondary-container: '#6d11ad'
  on-secondary-container: '#d7a4ff'
  tertiary: '#fff5f6'
  on-tertiary: '#65002e'
  tertiary-container: '#ffcfd9'
  on-tertiary-container: '#bd005c'
  error: '#ffb4ab'
  on-error: '#690005'
  error-container: '#93000a'
  on-error-container: '#ffdad6'
  primary-fixed: '#6ff6ff'
  primary-fixed-dim: '#00dce6'
  on-primary-fixed: '#002022'
  on-primary-fixed-variant: '#004f53'
  secondary-fixed: '#f2daff'
  secondary-fixed-dim: '#e0b6ff'
  on-secondary-fixed: '#2e004e'
  on-secondary-fixed-variant: '#6a0baa'
  tertiary-fixed: '#ffd9e1'
  tertiary-fixed-dim: '#ffb1c4'
  on-tertiary-fixed: '#3f001a'
  on-tertiary-fixed-variant: '#8f0044'
  background: '#101418'
  on-background: '#e0e3e8'
  surface-variant: '#31353a'
typography:
  headline-xl:
    fontFamily: Space Grotesk
    fontSize: 40px
    fontWeight: '700'
    lineHeight: 48px
    letterSpacing: -0.03em
  headline-xl-mobile:
    fontFamily: Space Grotesk
    fontSize: 30px
    fontWeight: '700'
    lineHeight: 36px
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Space Grotesk
    fontSize: 32px
    fontWeight: '600'
    lineHeight: 40px
    letterSpacing: -0.02em
  headline-lg-mobile:
    fontFamily: Space Grotesk
    fontSize: 24px
    fontWeight: '600'
    lineHeight: 32px
    letterSpacing: -0.01em
  headline-md:
    fontFamily: Space Grotesk
    fontSize: 20px
    fontWeight: '600'
    lineHeight: 28px
    letterSpacing: -0.01em
  body-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
    letterSpacing: 0em
  body-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 20px
    letterSpacing: 0.01em
  body-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 12px
    fontWeight: '400'
    lineHeight: 16px
    letterSpacing: 0.01em
  label-lg:
    fontFamily: Space Mono
    fontSize: 13px
    fontWeight: '700'
    lineHeight: 18px
    letterSpacing: 0.08em
  label-md:
    fontFamily: Space Mono
    fontSize: 11px
    fontWeight: '700'
    lineHeight: 16px
    letterSpacing: 0.06em
  label-sm:
    fontFamily: Space Mono
    fontSize: 10px
    fontWeight: '400'
    lineHeight: 14px
    letterSpacing: 0.04em
  telemetry-data:
    fontFamily: Space Mono
    fontSize: 12px
    fontWeight: '700'
    lineHeight: 14px
    letterSpacing: 0.02em
rounded:
  sm: 0.125rem
  DEFAULT: 0.25rem
  md: 0.375rem
  lg: 0.5rem
  xl: 0.75rem
  full: 9999px
spacing:
  gutter: 0.75rem
  gutter-desktop: 1.25rem
  margin: 1rem
  margin-desktop: 2rem
  space-xs: 0.25rem
  space-sm: 0.5rem
  space-md: 0.75rem
  space-lg: 1rem
  space-xl: 1.5rem
---

## Brand & Style

This design system expresses high-fidelity digital signal processing through the precision of industrial studio workstations, informed by the bespoke utility of Teenage Engineering and the kinetic depth of contemporary mobile platforms. It treats sound enhancement not as an invisible background task, but as a tactile instrument. 

The aesthetic merges **Precision Brutalism** with **Atmospheric Glassmorphism**: ultra-dark, light-absorbent obsidian substrates hold glowing visualizers, laser-sharp monospaced readouts, and hyper-responsive tactile controls. It evokes the feeling of commanding an advanced spatial audio console in low light. 

Primary user touchpoints target audiophiles, sound designers, and mobile hardware enthusiasts who demand granular acoustic control (parametric EQ, spatial convolution, multiband saturation) without clunky analog skeuomorphism. Interactive surfaces favor strict geometric structure, surgical telemetry feedback, and glowing capacitive interactions.

## Colors

The palette operates under a pure dark configuration designed for high-refresh-rate OLED panels, keeping standard black level power consumption minimal while driving high-contrast neon accents to prioritize signal intelligibility.

### Surface Architecture
* **Canvas Void (`#080C10`):** The foundational obsidian canvas.
* **Surface Container Base (`#0E141B`):** Base container elevation for inactive modules and parameter racks.
* **Surface Container High (`#151E28`):** Elevated cards, active processing units, and floating sheets.
* **Surface Highlight (`#1F2B3A`):** Low-contrast structural framing, inset slider troughs, and separator rules.

### Signal Accents
* **Primary / Electric Cyan (`#00F2FE`):** Main interactive controls, active DSP routing, master output levels, and active audio energy nodes.
* **Secondary / Ultraviolet (`#9D4EDD`):** Spatialization parameters, stereo imaging bounds, and harmonic excitation states.
* **Tertiary / Neon Crimson (`#FF007F`):** Clipping indicators, hard limiters, transient shaping warnings, and bypass states.
* **Telemetry Green (`#00E676`):** Phase coherence, low-latency lock, and bit-perfect DAC pass-through status.
* **Telemetry Amber (`#FFB300`):** Buffer headroom alerts, dithering flags, and soft-clip knee thresholds.

Text hierarchies derive strictly from neutral shifts: Primary labels glow at `rgba(240, 246, 252, 0.96)`, secondary telemetry reads at `rgba(148, 163, 184, 0.72)`, and structural inactive markers sit at `rgba(100, 116, 139, 0.35)`.

## Typography

Typography establishes an unequivocal distinction between control system readouts and UX navigation.

* **Space Grotesk** serves as the display engine for titles, operational modes, and main patch selection. Its mechanical curves echo modern audio computing hardware.
* **Plus Jakarta Sans** governs content body layers, explanatory documentation, and peripheral menu dialogues, maintaining legibility across high-density layouts.
* **Space Mono** executes all real-time audio metrics, decibel indices, frequency bins, sample rate values, and toggle badges. All data readouts, frequency axes (e.g., `20Hz - 20kHz`), and dynamic gain ceilings use strict tabular sizing to eliminate horizontal text jitter under real-time parameter streaming.

## Layout & Spacing

Layout geometry follows an intensive studio rack philosophy optimized for one-handed mobile manipulation alongside rapid horizontal tablet adjustments.

* **Grid Distribution:** The mobile system uses an adaptive fluid column arrangement (4-column mobile, 8-column tablet, 12-column wide desktop/dock). Processing modules and visualizer canvas elements snap tightly across full column spans to prevent dead screen space.
* **Rhythm System:** Driven by a base-4 grid with a dominant 8px increment. Vertical spacing within modules stays compressed (`space-sm` and `space-md`) to ensure critical processing nodes remain above the screen fold. Outer container padding expands via `margin` to keep edge controls clear of curved display edges and system gesture bars.
* **Reflow Logic:** On narrow screens, multi-band parametric controls collapse into swipeable horizontal band cards with a sticky global analyzer strip above. On wide surfaces, telemetry cards, dynamic nodes, and routing matrices dock side-by-side in modular rack bays.

## Elevation & Depth

Visual depth avoids soft generic dropshadows, relying instead on back-illuminated emission glows, dark-panel extrusion tiers, and micro-bordered glass elements.

* **Layer 0 (Canvas):** Pure `#080C10`, baseline zero illumination.
* **Layer 1 (Recessed Well):** Inset containers for sliders and channel strips. Achieved via `#05080B` with a sharp interior stroke (`rgba(0, 242, 254, 0.08)` top/bottom).
* **Layer 2 (Plates & Cards):** Surface `#0E141B` with 1px hairline outer borders (`rgba(255, 255, 255, 0.06)`). In active states, this stroke transitions to `rgba(0, 242, 254, 0.3)`.
* **Layer 3 (Floating HUD Overlays):** Semi-transparent frosted obsidian (`rgba(14, 20, 27, 0.75)`) utilizing an active backdrop blur filter (`20px`).
* **Luminescence (Neon Elevation):** Functional illumination replaces depth shadows. Active knobs, peak audio levels, and bypass buttons cast localized ambient halos: `0 0 16px rgba(0, 242, 254, 0.35)` for primary processing states and `0 0 16px rgba(157, 78, 221, 0.35)` for spatial stages.

## Shapes

The design system adopts a technical, compact radius model (`Level 1 - Soft`). Softened corners eliminate harsh pixel clipping on mobile displays while retaining the silhouette of high-precision machined aluminum audio interfaces.

* **Micro Components (Chips, Badges, Values):** Fixed radius of `0.25rem`.
* **Standard Interactive Controls (Buttons, Inputs, Knobs bases):** Subtle corner arc of `0.25rem` to keep interaction targets crisp.
* **Enclosures & Rack Modules (Cards, Visualizers, Sheets):** Capped at `0.5rem` (`rounded-lg`), creating modular separation between active DSP parameter sets.
* **Pill Exceptions:** Restricted solely to floating master playhead pills and spectral bypass buttons.

## Components

### Buttons & Interactive Triggers
* **Primary Command Button:** Solid high-visibility `#00F2FE` fill with `#080C10` Space Grotesk bold typography. Under touch, it triggers an interior cyan glow ring with instant 0-delay kinetic haptic response.
* **Secondary Rack Button:** `#151E28` surface, `1px` stroke using `rgba(255, 255, 255, 0.12)`, text set in `Space Mono` uppercase. Hover/press shifts stroke to `#00F2FE`.
* **Bypass Trigger:** Monospaced square toggle button with an integrated dual-state LED status indicator: dim red (`#FF007F` at 30%) when inactive; glowing electric teal (`#00F2FE`) when online.

### Tactile Sliders & Rotary Encoders
* **Faders / Sliders:** Inset linear rail channel (`#05080B`) containing a dynamic neon gradient progress fill (`#9D4EDD` shifting to `#00F2FE`). The fader thumb is a crisp 6px-wide rectangular machined bar capped with a vertical neon center-line marker.
* **Rotary Knobs:** Continuous circular scrubbers marked with peripheral tick lines. Knobs convey active value arcs via an outer SVG glowing sweep line with real-time numeric readouts centered in `Space Mono`.

### Chips & Filter Bins
* Compact tags displaying octave profiles, sample rates (e.g., `96kHz / 24bit`), and algorithm states. Constructed from high-density `#0E141B` backgrounds with active status border highlights. Selected chips project an inner neon glow.

### Lists & Channel Strips
* Low-profile strip dividers using crisp horizontal hairline borders (`rgba(255, 255, 255, 0.05)`). Left rail showcases dynamic VU meter levels; right rail houses primary values in monospace. Tapping an item reveals an inline collapsible rack drawer rather than pushing a screen transition.

### Cards & Module Enclosures
* Structural cards encapsulate discrete audio engines (e.g., "Multiband Dynamics", "Convolution Reverb"). Headers feature title text, bypass toggles, and minimized real-time oscilloscope previews. Backdrops use semi-translucent obsidian glass to reveal subtle blurred visualizer movement beneath.

### Specialized Components
* **Real-time FFT Spectrum Visualizer:** Full-width vector canvas rendered above the noise floor. Features customizable multi-point interactive control nodes that glow brightly under touch.
* **Audio Telemetry Badge:** Micro tabular display block for output headroom metrics, displaying `PEAK`, `RMS`, and `LUFS` with instant red alert changes during dynamic clipping.