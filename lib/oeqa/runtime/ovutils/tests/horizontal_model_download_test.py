class HorizontalModelDownloadTest(object):
    download_files = {'horizontal-text-detection-0001.xml': 'https://storage.openvinotoolkit.org/repositories/open_model_zoo/2023.0/models_bin/1/horizontal-text-detection-0001/FP32/horizontal-text-detection-0001.xml',
                      'horizontal-text-detection-0001.bin': 'https://storage.openvinotoolkit.org/repositories/open_model_zoo/2023.0/models_bin/1/horizontal-text-detection-0001/FP32/horizontal-text-detection-0001.bin'}


    def __init__(self, target, work_dir):
        self.target = target
        self.work_dir = work_dir

    def setup(self):
        self.target.run('mkdir -p %s' % self.work_dir)

    def tear_down(self):
        self.target.run('rm -rf %s' % self.work_dir)

    def test_can_download_horizontal_text_model_topology(self, proxy_port):
        print(f"https_proxy : {proxy_port}")
        return self.target.run('cd %s; wget %s -e https_proxy=%s' %
                               (self.work_dir,
                                self.download_files['horizontal-text-detection-0001.xml'],
                                proxy_port))

    def test_can_download_horizontal_text_model_weights(self, proxy_port):
        print(f"https_proxy : {proxy_port}")
        return self.target.run('cd %s; wget %s -e https_proxy=%s' %
                               (self.work_dir,
                                self.download_files['horizontal-text-detection-0001.bin'],
                                proxy_port))
