package de.muenchen.kobit.backend.downloads.view;

import de.muenchen.kobit.backend.downloads.model.Download;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DownloadView {

    String name;
    String ulr;

    public final Download toDownload() {
        return new Download(this.ulr, this.name);
    }
}
