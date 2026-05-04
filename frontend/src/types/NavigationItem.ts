export interface NavigationItem {
  title: string;
  props?: {
    prependIcon?: string;
    to?: string;
  };
  children?: NavigationItem[];
}
