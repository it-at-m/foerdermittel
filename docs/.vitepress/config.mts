import { defineConfig } from "vitepress";
import { withMermaid } from "vitepress-plugin-mermaid";

// https://vitepress.dev/reference/site-config
const vitepressConfig = defineConfig({
  base: "/foerdermittel/", // needs to be changed if delivered via sub path (e.g. "/docs/" for example.com/docs)
  title: "Fördermittel",
  description: "Documentation",
  head: [
    [
      "link",
      {
        rel: "icon",
        href: `https://assets.muenchen.de/logos/lhm/icon-lhm-muenchen-32.png`,
      },
    ],
  ],
  lastUpdated: true,
  themeConfig: {
    // https://vitepress.dev/reference/default-theme-config
    nav: [
      { text: "Home", link: "/" },
      {
              text: "Overview",
              link: "/overview",
            },
      {
        text: "Docs",
        items: [
          { text: "Architecture", link: "/architecture" },
          { text: "Develop", link: "/develop" },
        ],
      },
    ],
    sidebar: [
      {
        text: "Overview",
        link: "/overview",
      },
      {
        text: "Develop",
        link: "/develop",
        collapsed: true,
        items: [
          { text: "Setup and Tools", link: "/develop/setup-and-tools" },
          { text: "Development Process", link: "/develop/development-process" },
          { text: "Guides", link: "/develop/guides" },
        ],
      },
      {
        text: "Architecture",
        link: "/architecture",
        collapsed: true,
        items: [
          {
            text: "01 Introduction and Goals",
            link: "/architecture/01_introduction_and_goals",
          },
          {
            text: "02 Architecture Constraints",
            link: "/architecture/02_architecture_constraints",
          },
          { text: "03 Context and Scope", link: "/architecture/03_context_and_scope" },
          { text: "04 Solution Strategy", link: "/architecture/04_solution_strategy" },
          {
            text: "05 Building Block View",
            link: "/architecture/05_building_block_view",
          },
          { text: "06 Cross-cutting Concepts", link: "/architecture/06_concepts" },
          {
            text: "07 Architecture Decisions",
            link: "/adr",
          },
          { text: "08 Glossary", link: "/architecture/08_glossary" },
        ],
      },

      {
        text: "References",
        items: [
          {
            text: "Refarch - Reference architecture",
            link: "https://refarch.oss.muenchen.de",
          },
          {
            text: "Fördermittel Repository",
            link: "https://github.com/it-at-m/foerdermittel",
          },
        ],
      },
    ],
    socialLinks: [
      { icon: "github", link: "https://github.com/it-at-m/foerdermittel" },
    ],
    footer: {
      message: `<a href="https://opensource.muenchen.de/impress.html">Impress and Contact</a>`,
    },
    outline: {
      level: "deep",
    },
    search: {
      provider: "local",
    },
  },
  markdown: {
    image: {
      lazyLoading: true,
    },
  },
});

export default withMermaid(vitepressConfig);
